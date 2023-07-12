package com.swayy.core_network.model.live

data class Fixture(
    val date: String,
    val id: Int,
    val referee: String,
    val status: Status,
    val timezone: String
)