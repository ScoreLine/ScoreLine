package com.swayy.core.core

import com.swayy.core.domain.model.LeagueStanding
import com.swayy.core.domain.model.MatchInfo

data class MatchInfoState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val matchinfo: List<MatchInfo> = emptyList()
)
