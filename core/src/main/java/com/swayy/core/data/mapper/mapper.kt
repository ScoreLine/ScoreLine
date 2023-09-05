package com.swayy.core.data.mapper

import com.swayy.core.domain.model.LeagueStanding
import com.swayy.core.domain.model.LeagueStandingResponse
import com.swayy.core.domain.model.MatchInfo
import com.swayy.core.domain.model.MatchInfoResponse
import com.swayy.core.domain.model.WebMatch
import com.swayy.core.domain.model.WebMatchResponse

internal fun WebMatchResponse.toWebMatch(): WebMatch {
    return WebMatch(
        league,
        homeTeam,
        awayTeam,
        homeTeamImageUrl,
        awayTeamImageUrl,
        matchTime,
        round,
        matchLink,
        homeScore,
        awayScore,
        matchStatus,
        matchProgress,
        link,
        leagueImage,
        leagueLink
    )
}

internal fun LeagueStandingResponse.toStanding(): LeagueStanding {
    return LeagueStanding(
        position,
        logoUrl,
        teamName,
        points,
        matchesPlayed,
        wins,
        draws,
        losses,
        goalsFor,
        goalsAgainst,
        goalDifference
    )
}

internal fun MatchInfoResponse.toMatchInfo(): MatchInfo {
    return MatchInfo(
        homeTeam, awayTeam, homeLogo, awayLogo, score, date, time
    )
}