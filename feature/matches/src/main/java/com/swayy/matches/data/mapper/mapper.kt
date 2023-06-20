package com.swayy.matches.data.mapper

import android.service.autofill.FieldClassification
import com.swayy.core_database.model.MatchesEntity
import com.swayy.core_network.model.Fixtures.Response
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