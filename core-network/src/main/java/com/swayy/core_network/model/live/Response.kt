package com.swayy.core_network.model.live

data class Response(
    val events: List<Event>,
    val fixture: Fixture,
    val goals: Goals,
    val league: League,
    val score: Score,
    val teams: Teams
)