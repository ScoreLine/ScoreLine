package com.swayy.core_network.model.assists

data class Response(
    val player: com.swayy.core_network.model.assists.Player,
    val statistics: List<com.swayy.core_network.model.assists.Statistic>
)