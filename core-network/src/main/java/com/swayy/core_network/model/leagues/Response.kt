package com.swayy.core_network.model.leagues

data class Response(
    val country: Country,
    val league: League,
    val seasons: List<Season>
)