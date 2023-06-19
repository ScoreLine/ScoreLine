package com.swayy.matches.domain.model

import com.swayy.core_network.model.Fixtures.Fixture
import com.swayy.core_network.model.Fixtures.Goals
import com.swayy.core_network.model.Fixtures.League
import com.swayy.core_network.model.Fixtures.Score
import com.swayy.core_network.model.Fixtures.Teams

data class Match(
    val fixture: Fixture,
    val goals: Goals,
    val league: League,
    val score: Score,
    val teams: Teams
)
