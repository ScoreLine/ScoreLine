package com.swayy.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swayy.core_network.model.stats.Statistic
import com.swayy.core_network.model.stats.Team

@Entity(tableName = "stats_table")
data class StatsEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val statistics: List<Statistic>,
    val team: Team
)
