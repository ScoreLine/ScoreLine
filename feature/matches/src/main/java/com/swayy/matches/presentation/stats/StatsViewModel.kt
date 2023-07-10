package com.swayy.matches.presentation.stats

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.matches.domain.use_case.GetStatsUseCase
import com.swayy.matches.presentation.state.StatsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val getStatsUseCase: GetStatsUseCase
): ViewModel() {
    private val _stats = mutableStateOf(StatsState())
    val stats: State<StatsState> = _stats

    private var isDataLoaded = false

    fun getStats(fixture: String) {
        viewModelScope.launch {
            try {
                _stats.value = StatsState(isLoading = true)

                getStatsUseCase(fixture = fixture).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _stats.value = StatsState(stats = result.data ?: emptyList())
                            isDataLoaded = true
                        }
                        is Resource.Error -> {
                            _stats.value = StatsState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                        is Resource.Loading -> {
                            // No need to update _stats for Loading state, as it's already set above
                        }
                    }
                }
            } catch (e: Exception) {
                _stats.value = StatsState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }
}