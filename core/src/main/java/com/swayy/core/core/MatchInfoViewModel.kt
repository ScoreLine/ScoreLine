package com.swayy.core.core

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.domain.repository.MatchInfoRepository
import com.swayy.core.leagueStanding.LeagueStandingState
import com.swayy.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchInfoViewModel @Inject constructor(
    private val repository: MatchInfoRepository
) : ViewModel() {

    private val _matchinfo = mutableStateOf(MatchInfoState())
    val matchinfo: State<MatchInfoState> = _matchinfo

    fun getMatchInfo(matchLink: String) {
        _matchinfo.value = matchinfo.value.copy(
            isLoading = true,
            matchinfo = emptyList(),
            error = null
        )
        viewModelScope.launch {
            when (val result = repository.getMatchInfo(url = matchLink)) {
                is Resource.Error -> {
                    _matchinfo.value = matchinfo.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                is Resource.Success -> {
                    _matchinfo.value = matchinfo.value.copy(
                        isLoading = false,
                        matchinfo = result.data ?: emptyList()
                    )
                }

                else -> {
                    matchinfo
                }
            }
        }
    }
}