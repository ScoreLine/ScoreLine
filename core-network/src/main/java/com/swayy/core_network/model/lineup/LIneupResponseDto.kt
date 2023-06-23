package com.swayy.core_network.model.lineup

data class LIneupResponseDto(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)