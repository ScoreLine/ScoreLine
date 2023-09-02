package com.swayy.core.domain.model

data class LeagueStanding(
    val position: Int,
    val logoUrl: String,
    val teamName: String,
    val points: Int,
    val matchesPlayed: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int
)
