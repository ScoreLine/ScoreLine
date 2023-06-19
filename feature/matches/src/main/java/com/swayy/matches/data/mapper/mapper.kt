package com.swayy.matches.data.mapper

import com.swayy.core_network.model.Fixtures.Response
import com.swayy.matches.domain.model.Match

internal fun Response.toDomain(): Match {
    return Match(
        fixture, goals, league, score, teams
    )
}