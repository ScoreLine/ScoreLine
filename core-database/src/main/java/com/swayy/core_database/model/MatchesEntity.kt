package com.swayy.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swayy.core_network.model.Fixtures.Fixture
import com.swayy.core_network.model.Fixtures.Goals
import com.swayy.core_network.model.Fixtures.League
import com.swayy.core_network.model.Fixtures.Score
import com.swayy.core_network.model.Fixtures.Teams

@Entity(tableName = "matches_table")
data class MatchesEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val fixture: Fixture,
    val goals: Goals,
    val league: League,
    val score: Score,
    val teams: Teams
)
