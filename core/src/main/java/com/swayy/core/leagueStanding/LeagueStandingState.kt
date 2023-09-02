package com.swayy.core.leagueStanding

import com.swayy.core.domain.model.LeagueStanding
import com.swayy.core.domain.model.WebMatch

data class LeagueStandingState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val standings: List<LeagueStanding> = emptyList()
)
