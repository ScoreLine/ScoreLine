package com.swayy.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val imageUrl: String,
    val websiteUrl: String,
    val isFavorite: Boolean = false
)
