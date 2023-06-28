package com.example.standings.data.mapper

import com.example.standings.domain.model.StandingsDomainModel
import com.swayy.core_database.model.StandingsEntity
import com.swayy.core_network.model.standings.League

internal fun League.toStandingsEntity(): StandingsEntity {
    return StandingsEntity(
        country, flag, id, logo, name, season, standings
    )
}

internal fun StandingsEntity.toStandingsDomain() : StandingsDomainModel{
    return StandingsDomainModel(country, flag, id, logo, name, season, standings)
}