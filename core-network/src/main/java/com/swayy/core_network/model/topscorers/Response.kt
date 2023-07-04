package com.swayy.core_network.model.topscorers

data class Response(
    val player: com.swayy.core_network.model.topscorers.Player,
    val statistics: List<com.swayy.core_network.model.topscorers.Statistic>
)