package com.swayy.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swayy.core_network.model.lineup.Coach
import com.swayy.core_network.model.lineup.StartXI
import com.swayy.core_network.model.lineup.Substitute
import com.swayy.core_network.model.lineup.Team

@Entity(tableName = "lineup_table")
data class LineupEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val coach: Coach?,
    val formation: String?,
    val startXI: List<StartXI>?,
    val substitutes: List<Substitute>?,
    val team: Team?
)
