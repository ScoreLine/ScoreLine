package com.example.standings.presentation.scorers_assists

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standings.domain.model.TopAssistsDomainModel
import com.example.standings.domain.model.TopScorersDomainModel
import com.example.standings.domain.repo.usecase.AssistsUseCase
import com.example.standings.domain.repo.usecase.ScorersUseCase
import com.swayy.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScorersAndAssistsViewModel @Inject constructor(
    private val assistsUseCase: AssistsUseCase,
    private val scorersUseCase: ScorersUseCase
): ViewModel() {

    private val _assists = mutableStateOf(AssistsState())
    val assists: State<AssistsState> = _assists

    private val _scorers = mutableStateOf(ScorersState())
    val scorers: State<ScorersState> = _scorers

    private var isDataLoaded = false

    fun getTopScorers(league: Int, season:Int) {
        viewModelScope.launch {
            try {
                _scorers.value = ScorersState(isLoading = true)

                scorersUseCase(league = league, season = season).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _scorers.value = ScorersState(scorers = result.data ?: emptyList())
                            isDataLoaded = true
                        }
                        is Resource.Error -> {
                            _scorers.value = ScorersState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                        is Resource.Loading -> {
                            // No need to update _standings for Loading state, as it's already set above
                        }
                    }
                }
            } catch (e: Exception) {
                _scorers.value = ScorersState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }

    fun getTopAssists(league: Int, season:Int) {
        viewModelScope.launch {
            try {
                _assists.value = AssistsState(isLoading = true)

                assistsUseCase(league = league, season = season).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _assists.value = AssistsState(assists = result.data ?: emptyList())
                            isDataLoaded = true
                        }
                        is Resource.Error -> {
                            _assists.value = AssistsState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                        is Resource.Loading -> {
                            // No need to update _assists for Loading state, as it's already set above
                        }
                    }
                }
            } catch (e: Exception) {
                _assists.value = AssistsState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }

}

data class ScorersState (
    val isLoading: Boolean = false,
    val error: String = "",
    val scorers: List<TopScorersDomainModel> = emptyList()
)

data class AssistsState (
    val isLoading: Boolean = false,
    val error: String = "",
    val assists: List<TopAssistsDomainModel> = emptyList()
)
