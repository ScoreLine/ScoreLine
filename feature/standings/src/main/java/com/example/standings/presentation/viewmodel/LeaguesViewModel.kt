package com.example.standings.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standings.domain.model.LeaguesDomainModel
import com.example.standings.domain.repo.StandingsUseCase
import com.swayy.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaguesViewModel @Inject constructor(
    private val standingsUseCase: StandingsUseCase
): ViewModel() {
    private val _leagues = mutableStateOf(LeaguesState())
    val leagues: State<LeaguesState> = _leagues

    private var isDataLoaded = false

    fun getLeagues() {
        viewModelScope.launch {
            try {
                _leagues.value = LeaguesState(isLoading = true)

                standingsUseCase().collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _leagues.value = LeaguesState(leagues = result.data ?: emptyList())
                            isDataLoaded = true
                        }
                        is Resource.Error -> {
                            _leagues.value = LeaguesState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                        is Resource.Loading -> {
                            // No need to update _leagues for Loading state, as it's already set above
                        }
                    }
                }
            } catch (e: Exception) {
                _leagues.value = LeaguesState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }
}

data class LeaguesState (
    val isLoading: Boolean = false,
    val error: String = "",
    val leagues: List<LeaguesDomainModel> = emptyList()
)

