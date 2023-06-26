package com.swayy.matches.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.matches.domain.model.Match
import com.swayy.matches.domain.use_case.GetMatchUseCase
import com.swayy.matches.presentation.state.MatchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MatchViewmodel @Inject constructor(
    private val getMatchUseCase: GetMatchUseCase
) : ViewModel() {

    private val _matches = mutableStateOf(MatchState())
    val matches: State<MatchState> = _matches

    private var isDataLoaded = false

    fun getMatches(date: String) {
        viewModelScope.launch {
            try {
                _matches.value = MatchState(isLoading = true)

                getMatchUseCase(date = date).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _matches.value = MatchState(matches = result.data ?: emptyList())
                            isDataLoaded = true
                        }
                        is Resource.Error -> {
                            _matches.value = MatchState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                        is Resource.Loading -> {
                            // No need to update _matches for Loading state, as it's already set above
                        }
                    }
                }
            } catch (e: Exception) {
                _matches.value = MatchState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }
}