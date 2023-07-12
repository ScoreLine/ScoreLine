package com.swayy.matches.data.mapper


import com.swayy.core_database.model.EventsEntity
import com.swayy.core_database.model.HeadToHeadEntity
import com.swayy.core_database.model.LineupEntity
import com.swayy.core_database.model.LiveMatchesEntity
import com.swayy.core_database.model.MatchesEntity
import com.swayy.core_database.model.StatsEntity
import com.swayy.core_network.model.Fixtures.Response
import com.swayy.matches.domain.model.Events
import com.swayy.matches.domain.model.HeadToHeadDomainModel
import com.swayy.matches.domain.model.Lineup
import com.swayy.matches.domain.model.LiveMatchesDomain
import com.swayy.matches.domain.model.Match
import com.swayy.matches.domain.model.StatsDomainModel

internal fun MatchesEntity.toDomain(): Match {
    return Match(
        fixture, goals, league, score, teams
    )
}

internal fun Response.toEntity(): MatchesEntity {
    return MatchesEntity(
        id = 0,
        fixture = fixture,
        goals = goals,
        league = league,
        score = score,
        teams = teams
    )
}

internal fun com.swayy.core_network.model.lineup.Response.toLineupEntity(): LineupEntity? {
    return coach?.let {
        formation?.let { it1 ->
            startXI?.let { it2 ->
                substitutes?.let { it3 ->
                    team?.let { it4 ->
                        LineupEntity(
                            id = 0,
                            it, it1, it2, it3, it4
                        )
                    }
                }
            }
        }
    }
}

internal fun LineupEntity.toLineupDomain(): Lineup {
    return Lineup(coach, formation, startXI, substitutes, team)
}

internal fun com.swayy.core_network.model.events.Response.toEventsEntity(): EventsEntity {
    return EventsEntity(
        id = 0,
        assist = assist,
        comments = comments,
        detail, player, team, time, type

    )
}

internal fun EventsEntity.toEventsDomain(): Events {
    return Events(
        id, assist, comments, detail, player, team, time, type
    )
}


internal fun com.swayy.core_network.model.stats.Response.toStatsEntity(): StatsEntity {
    return StatsEntity(
        id = 0,statistics, team
    )
}

internal fun StatsEntity.toStatsDomain(): StatsDomainModel {
    return StatsDomainModel(
        statistics, team
    )
}

internal fun com.swayy.core_network.model.headtohead.Response.toHeadToHeadEntity(): HeadToHeadEntity {
    return HeadToHeadEntity(
        id = 0,goals, teams, league
    )
}

internal fun HeadToHeadEntity.toHead2HeadDomain(): HeadToHeadDomainModel {
    return HeadToHeadDomainModel(
        goals, teams, league
    )
}

internal fun com.swayy.core_network.model.live.Response.toLiveMatchesEntity(): LiveMatchesEntity {
    return LiveMatchesEntity(
        id = 0,fixture, league, teams, goals
    )
}

internal fun LiveMatchesEntity.toLiveMatchesDomain(): LiveMatchesDomain{
    return com.swayy.matches.domain.model.LiveMatchesDomain(
        fixture, league, teams, goals
    )
}