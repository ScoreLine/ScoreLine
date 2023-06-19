package com.swayy.core_network.model.Fixtures

data class FixturesResponseDto(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)