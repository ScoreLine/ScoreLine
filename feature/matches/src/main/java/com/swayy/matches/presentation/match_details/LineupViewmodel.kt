package com.swayy.matches.presentation.match_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.matches.domain.use_case.GetLineupUseCase
import com.swayy.matches.presentation.state.LineupState
import com.swayy.matches.presentation.state.MatchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LineupViewmodel @Inject constructor(
    private val getLineupUseCase: GetLineupUseCase
) : ViewModel(){
    private val _lineup = mutableStateOf(LineupState())
    val lineup: State<LineupState> = _lineup

    private var isLineupDataLoaded = false
    private var isLineupFlowCollected = false

    fun getLineup(fixture: Int) {
        if (!isLineupFlowCollected) {
            viewModelScope.launch {
                try {
                    _lineup.value = LineupState(isLoading = true)

                    getLineupUseCase(fixture = fixture).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _lineup.value = LineupState(lineup = result.data ?: emptyList())
                                isLineupDataLoaded = true
                            }
                            is Resource.Error -> {
                                _lineup.value = LineupState(
                                    error = result.message ?: "An unexpected error occurred"
                                )
                            }
                            is Resource.Loading -> {
                                // No need to update _lineup for Loading state, as it's already set above
                            }
                        }
                    }
                } catch (e: Exception) {
                    _lineup.value = LineupState(
                        error = "An unexpected error occurred: ${e.message}"
                    )
                }
            }
            isLineupFlowCollected = true
        } else if (isLineupDataLoaded) {
            // Data has already been loaded, no need to fetch again
        }
    }

}