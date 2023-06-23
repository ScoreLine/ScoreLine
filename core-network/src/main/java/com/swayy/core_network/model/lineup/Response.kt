package com.swayy.core_network.model.lineup

data class Response(
    val coach: Coach,
    val formation: String,
    val startXI: List<StartXI>,
    val substitutes: List<Substitute>,
    val team: Team
)