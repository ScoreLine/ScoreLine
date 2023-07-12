package com.swayy.matches.domain.model

import com.swayy.core_network.model.live.Fixture
import com.swayy.core_network.model.live.Goals
import com.swayy.core_network.model.live.League
import com.swayy.core_network.model.live.Teams

data class LiveMatchesDomain(
    val fixture: Fixture,
    val league: League,
    val teams: Teams,
    val goals: Goals,
)