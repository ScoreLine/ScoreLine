package com.swayy.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "soccer_table")
data class SoccerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val leagueName:String,
    val games:String,
    val teams:String,
    val logo:String,
    val flag :String,
    val link:String
)
