package com.swayy.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swayy.core_network.model.events.Assist
import com.swayy.core_network.model.events.Player
import com.swayy.core_network.model.events.Team
import com.swayy.core_network.model.events.Time

@Entity(tableName = "events_table")
data class EventsEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val assist: Assist?,
    val comments: String?,
    val detail: String?,
    val player: Player?,
    val team: Team?,
    val time: Time?,
    val type: String?
)
