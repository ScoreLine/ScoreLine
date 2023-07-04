package com.swayy.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clubs_table")
data class ClubsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val imageUrl: String,
    val websiteUrl: String
)
