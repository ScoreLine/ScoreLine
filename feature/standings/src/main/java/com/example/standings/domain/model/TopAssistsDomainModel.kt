package com.example.standings.domain.model

data class TopAssistsDomainModel (
    val player: com.swayy.core_network.model.assists.Player,
    val statistics: List<com.swayy.core_network.model.assists.Statistic>
)