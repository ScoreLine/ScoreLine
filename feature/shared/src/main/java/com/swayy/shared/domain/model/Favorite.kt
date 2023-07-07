package com.swayy.shared.domain.model

data class Favorite(
    val id: Int? = null,
    val name: String = "",
    val imageUrl: String = "",
    val websiteUrl: String = "",
    val isFavorite: Boolean = false
)
