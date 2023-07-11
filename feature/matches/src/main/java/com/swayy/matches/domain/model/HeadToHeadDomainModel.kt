package com.swayy.matches.domain.model

import com.swayy.core_network.model.headtohead.Goals
import com.swayy.core_network.model.headtohead.League
import com.swayy.core_network.model.headtohead.Teams

data class HeadToHeadDomainModel(
    val goals: Goals,
    val teams: Teams,
    val league: League
)
