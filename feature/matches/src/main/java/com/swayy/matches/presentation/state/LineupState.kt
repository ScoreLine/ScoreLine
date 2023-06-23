package com.swayy.matches.presentation.state

import com.swayy.matches.domain.model.Lineup
import com.swayy.matches.domain.model.Match

data class LineupState(
    val isLoading: Boolean = false,
    val error: String = "",
    val lineup: List<Lineup> = emptyList()
)
