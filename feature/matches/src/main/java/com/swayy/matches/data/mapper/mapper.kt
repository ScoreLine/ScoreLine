package com.swayy.matches.data.mapper


import com.swayy.core_database.model.EventsEntity
import com.swayy.core_database.model.LineupEntity
import com.swayy.core_database.model.MatchesEntity
import com.swayy.core_network.model.Fixtures.Response
import com.swayy.matches.domain.model.Events
import com.swayy.matches.domain.model.Lineup
import com.swayy.matches.domain.model.Match

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

