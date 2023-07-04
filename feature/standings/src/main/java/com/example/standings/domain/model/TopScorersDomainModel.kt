package com.example.standings.domain.model

import com.swayy.core_network.model.topscorers.Penalty
import com.swayy.core_network.model.topscorers.Player

data class TopScorersDomainModel (
    val player: com.swayy.core_network.model.topscorers.Player,
    val statistics: List<com.swayy.core_network.model.topscorers.Statistic>
)