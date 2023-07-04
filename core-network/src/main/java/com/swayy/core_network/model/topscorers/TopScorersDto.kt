package com.swayy.core_network.model.topscorers

data class TopScorersDto(
    val errors: List<Any>,
    val `get`: String,
    val paging: com.swayy.core_network.model.topscorers.Paging,
    val parameters: com.swayy.core_network.model.topscorers.Parameters,
    val response: List<com.swayy.core_network.model.topscorers.Response>,
    val results: Int
)