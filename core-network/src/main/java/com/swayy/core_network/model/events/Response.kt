package com.swayy.core_network.model.events

data class Response(
    val assist: Assist,
    val comments: String,
    val detail: String,
    val player: Player,
    val team: Team,
    val time: Time,
    val type: String
)