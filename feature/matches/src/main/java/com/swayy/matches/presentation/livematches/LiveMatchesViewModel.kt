package com.swayy.matches.presentation.livematches

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.matches.domain.model.LiveMatchesDomain
import com.swayy.matches.domain.use_case.GetLiveMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveMatchesViewModel @Inject constructor(
    private val getLiveMatchesUseCase: GetLiveMatchesUseCase
) : ViewModel() {

    private val _liveMatches = mutableStateOf(LiveMatchesState())
    val liveMatches: State<LiveMatchesState> = _liveMatches

    private var isDataLoaded = false


    fun getLiveMatches(live: String) {
        viewModelScope.launch {
            try {
                _liveMatches.value = LiveMatchesState(isLoading = true)

                getLiveMatchesUseCase(live = live).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _liveMatches.value = LiveMatchesState(liveMatches = result.data ?: emptyList())
                            isDataLoaded = true
                        }
                        is Resource.Error -> {
                            _liveMatches.value = LiveMatchesState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                        is Resource.Loading -> {
                            // No need to update _liveMatches for Loading state, as it's already set above
                        }
                    }
                }
            } catch (e: Exception) {
                _liveMatches.value = LiveMatchesState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }
}

data class LiveMatchesState(
    val isLoading: Boolean = false,
    val error: String = "",
    val liveMatches: List<LiveMatchesDomain> = emptyList()
)
