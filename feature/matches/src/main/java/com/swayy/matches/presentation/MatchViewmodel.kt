package com.swayy.matches.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.matches.domain.use_case.GetMatchUseCase
import com.swayy.matches.presentation.state.MatchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MatchViewmodel @Inject constructor(
    private val getMatchUseCase: GetMatchUseCase
) : ViewModel(){

    private val _matches = mutableStateOf(MatchState())
    val matches: State<MatchState> = _matches

    fun getMatches(
        date:String
    ) {
        getMatchUseCase(
            date = date
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _matches.value = MatchState(matches = result.data ?: emptyList())

                }

                is Resource.Error -> {
                    _matches.value = MatchState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _matches.value = MatchState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


}