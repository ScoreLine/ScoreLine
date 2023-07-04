package com.swayy.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swayy.core_network.model.assists.Goals
import com.swayy.core_network.model.leagues.Country
import com.swayy.core_network.model.leagues.League
import com.swayy.core_network.model.standings.Standing
import com.swayy.core_network.model.topscorers.Penalty
import com.swayy.core_network.model.topscorers.Player
import com.swayy.core_network.model.topscorers.Statistic

@Entity(tableName = "standings_table")
data class StandingsEntity (
    val country: String,
    val flag: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val logo: String,
    val name: String,
    val season: Int,
    val standings: List<List<Standing>>
)

@Entity(tableName = "leagues_table")
data class LeaguesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val country: Country,
    val league: League,
)

@Entity(tableName = "topscorers_table")
data class TopScorersEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val player: com.swayy.core_network.model.topscorers.Player,
    val statistics: List<com.swayy.core_network.model.topscorers.Statistic>
)

@Entity(tableName = "topassists_table")
data class TopAssistsEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val player: com.swayy.core_network.model.assists.Player,
    val statistics: List<com.swayy.core_network.model.assists.Statistic>

)