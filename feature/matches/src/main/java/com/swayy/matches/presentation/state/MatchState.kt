package com.swayy.matches.presentation.state

import com.swayy.matches.domain.model.Match

data class MatchState(
    val isLoading: Boolean = false,
    val error: String = "",
    val matches: List<Match> = emptyList()
)
