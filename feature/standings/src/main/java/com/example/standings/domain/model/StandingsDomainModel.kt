package com.example.standings.domain.model

import com.swayy.core_network.model.standings.Standing

data class StandingsDomainModel(
    val country: String,
    val flag: String,
    val id: Int,
    val logo: String,
    val name: String,
    val season: Int,
    val standings: List<List<Standing>>
)
