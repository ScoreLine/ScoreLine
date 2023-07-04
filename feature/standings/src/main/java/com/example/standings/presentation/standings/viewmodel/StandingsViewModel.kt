package com.example.standings.presentation.standings.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standings.domain.model.StandingsDomainModel
import com.example.standings.domain.repo.usecase.StandingsUseCase
import com.swayy.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StandingsViewModel @Inject constructor(
    private val standingsUseCase: StandingsUseCase
): ViewModel() {
    private val _standings = mutableStateOf(StandingsState())
    val standings: State<StandingsState> = _standings

    private var isDataLoaded = false

    fun getStandings(league: Int, season:Int) {
        viewModelScope.launch {
            try {
                _standings.value = StandingsState(isLoading = true)

                standingsUseCase(league = league, season = season).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _standings.value = StandingsState(standings = result.data ?: emptyList())
                            isDataLoaded = true
                        }
                        is Resource.Error -> {
                            _standings.value = StandingsState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                        is Resource.Loading -> {
                            // No need to update _standings for Loading state, as it's already set above
                        }
                    }
                }
            } catch (e: Exception) {
                _standings.value = StandingsState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }
}

data class StandingsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val standings: List<StandingsDomainModel> = emptyList()
)


