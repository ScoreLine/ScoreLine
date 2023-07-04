package com.swayy.core_network.model.assists

data class AssistsDto(
    val errors: List<Any>,
    val `get`: String,
    val paging: com.swayy.core_network.model.assists.Paging,
    val parameters: com.swayy.core_network.model.assists.Parameters,
    val response: List<com.swayy.core_network.model.assists.Response>,
    val results: Int
)