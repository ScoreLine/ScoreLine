package com.swayy.matches.presentation.match_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.matches.domain.use_case.GetLineupUseCase
import com.swayy.matches.presentation.events.state.EventsState
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

    fun getLineup(fixture: Int) {
        viewModelScope.launch {
            getLineupUseCase.invoke(fixture = fixture).collect { result ->
                when (result) {

                    is Resource.Success -> {
                        _lineup.value =
                            LineupState(lineup = result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _lineup.value = LineupState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                        Log.d("DETAILS:: ", result.message.toString())
                    }
                    is Resource.Loading -> {
                        _lineup.value = LineupState(isLoading = true)
                        Log.d("DETAILS:: ", "Loading.....")
                    }
                }
            }
        }
    }

}