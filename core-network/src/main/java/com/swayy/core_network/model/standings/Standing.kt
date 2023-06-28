package com.swayy.core_network.model.standings

data class Standing(
    val all: All,
    val away: Away,
    val description: String,
    val form: String,
    val goalsDiff: Int,
    val group: String,
    val home: Home,
    val points: Int,
    val rank: Int,
    val status: String,
    val team: Team,
    val update: String
)