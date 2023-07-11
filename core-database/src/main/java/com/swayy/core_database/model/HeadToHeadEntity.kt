package com.swayy.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swayy.core_network.model.headtohead.Goals
import com.swayy.core_network.model.headtohead.League
import com.swayy.core_network.model.headtohead.Teams

@Entity(tableName = "h2h_table")
data class HeadToHeadEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val goals: Goals,
    val teams: Teams,
    val league: League
)
