package com.swayy.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swayy.core_network.model.live.Fixture
import com.swayy.core_network.model.live.Goals
import com.swayy.core_network.model.live.League
import com.swayy.core_network.model.live.Teams


@Entity(tableName = "livematches_table")
data class LiveMatchesEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val fixture: Fixture,
    val league: League,
    val teams: Teams,
    val goals: Goals,
)
