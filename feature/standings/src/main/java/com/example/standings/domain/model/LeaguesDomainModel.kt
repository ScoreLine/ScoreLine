package com.example.standings.domain.model

import com.swayy.core_network.model.leagues.Country
import com.swayy.core_network.model.leagues.League

data class LeaguesDomainModel (
    val country: Country,
    val league: League,
)