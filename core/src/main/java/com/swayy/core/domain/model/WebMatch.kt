package com.swayy.core.domain.model

data class WebMatch(
    val league: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamImageUrl: String,
    val awayTeamImageUrl: String,
    val matchTime: String,
    val round: String,
    val matchLink: String,
    var homeScore: String,
    var awayScore: String,
    var matchStatus: String = "",
    val matchProgress:String,
    val link:String,
    val leagueImage:String,
    val leagueLink:String
)
