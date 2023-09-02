package com.swayy.core.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.domain.repository.WebMatchRepository
import com.swayy.core.util.Resource
import com.swayy.core.util.UiEvents
import com.swayy.core.data.WebMatchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebMatchViewModel @Inject constructor(
    private val repository: WebMatchRepository
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow

    private val _matches = mutableStateOf(WebMatchState())
    val matches: State<WebMatchState> = _matches

    fun getWebMatch(url: String) {
        _matches.value = matches.value.copy(
            isLoading = true,
            matches = emptyList(),
            error = null
        )
        viewModelScope.launch {
            when (val result = repository.getWebMatches(url = url)) {
                is Resource.Error -> {
                    _matches.value = matches.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                is Resource.Success -> {
                    _matches.value = matches.value.copy(
                        isLoading = false,
                        matches = result.data ?: emptyList()
                    )
                }

                else -> {
                    matches
                }
            }
        }
    }
}