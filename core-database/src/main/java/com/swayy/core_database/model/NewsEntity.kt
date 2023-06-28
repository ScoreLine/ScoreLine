package com.swayy.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val imageUrl: String,
    val description: String,
    val url: String
)
