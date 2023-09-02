package com.swayy.core.leagueStanding

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.data.WebMatchState
import com.swayy.core.domain.repository.LeagueStandingRepository
import com.swayy.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueStandingViewModel @Inject constructor(
    private val repository: LeagueStandingRepository
) : ViewModel() {

    private val _standngs = mutableStateOf(LeagueStandingState())
    val standings: State<LeagueStandingState> = _standngs

    fun getLeagueStanding(league: String) {
        _standngs.value = standings.value.copy(
            isLoading = true,
            standings = emptyList(),
            error = null
        )
        viewModelScope.launch {
            when (val result = repository.getLeagueStanding(league = league)) {
                is Resource.Error -> {
                    _standngs.value = standings.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                is Resource.Success -> {
                    _standngs.value = standings.value.copy(
                        isLoading = false,
                        standings = result.data ?: emptyList()
                    )
                }

                else -> {
                    standings
                }
            }
        }
    }
}