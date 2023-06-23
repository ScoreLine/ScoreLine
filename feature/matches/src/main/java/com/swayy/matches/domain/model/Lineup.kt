package com.swayy.matches.domain.model

import com.swayy.core_network.model.lineup.Coach
import com.swayy.core_network.model.lineup.StartXI
import com.swayy.core_network.model.lineup.Substitute
import com.swayy.core_network.model.lineup.Team

data class Lineup(
    val coach: Coach,
    val formation: String,
    val startXI: List<StartXI>,
    val substitutes: List<Substitute>,
    val team: Team
)
