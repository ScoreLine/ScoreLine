package com.swayy.matches.presentation.events

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.matches.domain.use_case.GetEventsUseCase
import com.swayy.matches.presentation.events.state.EventsState
import com.swayy.matches.presentation.state.LineupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {

    private val _matches = mutableStateOf(EventsState())
    val matches: State<EventsState> = _matches

    private var isDataLoaded = false
    private var isFlowCollected = false

    fun getEvents(fixture: Int) {
        if (!isDataLoaded && !isFlowCollected) {
            viewModelScope.launch {
                try {
                    _matches.value = EventsState(isLoading = true)

                    getEventsUseCase(fixture = fixture).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _matches.value = EventsState(events = result.data ?: emptyList())
                                isDataLoaded = true
                            }

                            is Resource.Error -> {
                                _matches.value = EventsState(
                                    error = result.message ?: "An unexpected error occurred"
                                )
                            }

                            is Resource.Loading -> {
                                // No need to update _matches for Loading state, as it's already set above
                            }
                        }
                    }
                } catch (e: Exception) {
                    _matches.value = EventsState(
                        error = "An unexpected error occurred: ${e.message}"
                    )
                }
            }
            isFlowCollected = true
        } else if (isDataLoaded) {
            // Data has already been loaded, no need to fetch again
        }
    }
}