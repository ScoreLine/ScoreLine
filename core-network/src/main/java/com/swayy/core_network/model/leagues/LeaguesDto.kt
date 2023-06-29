package com.swayy.core_network.model.leagues

data class LeaguesDto(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: List<Any>,
    val response: List<Response>,
    val results: Int
)