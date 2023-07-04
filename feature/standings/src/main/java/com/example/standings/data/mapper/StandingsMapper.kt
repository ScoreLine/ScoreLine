package com.example.standings.data.mapper

import com.example.standings.domain.model.LeaguesDomainModel
import com.example.standings.domain.model.StandingsDomainModel
import com.example.standings.domain.model.TopAssistsDomainModel
import com.example.standings.domain.model.TopScorersDomainModel
import com.swayy.core_database.model.LeaguesEntity
import com.swayy.core_database.model.StandingsEntity
import com.swayy.core_database.model.TopAssistsEntity
import com.swayy.core_database.model.TopScorersEntity
import com.swayy.core_network.model.leagues.LeaguesDto
import com.swayy.core_network.model.leagues.Response
import com.swayy.core_network.model.standings.League

internal fun League.toStandingsEntity(): StandingsEntity {
    return StandingsEntity(
        country, flag, id, logo, name, season, standings
    )
}

internal fun StandingsEntity.toStandingsDomain() : StandingsDomainModel{
    return StandingsDomainModel(country, flag, id, logo, name, season, standings)
}


internal fun Response.toLeaguesEntity(): LeaguesEntity {
    return LeaguesEntity(
        id = 0,country, league
    )
}

internal fun LeaguesEntity.toLeaguesDomainModel() : LeaguesDomainModel {
    return LeaguesDomainModel(country, league)
}

internal fun com.swayy.core_network.model.topscorers.Response.toTopScorersEntity(): TopScorersEntity {
    return TopScorersEntity(
        id = 0,player,statistics
    )
}

internal fun TopScorersEntity.toTopScorersDomain(): TopScorersDomainModel {
    return TopScorersDomainModel(
        player, statistics
    )
}

internal fun com.swayy.core_network.model.assists.Response.toTopAssistsEntity(): TopAssistsEntity {
    return TopAssistsEntity(
        id = 0,player,statistics
    )
}

internal fun TopAssistsEntity.toTopAssistsDomain(): TopAssistsDomainModel {
    return TopAssistsDomainModel(
        player, statistics
    )
}