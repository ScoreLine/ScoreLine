package com.swayy.shared.data.mapper

import com.swayy.core_database.model.ClubsEntity
import com.swayy.core_database.model.FavoriteEntity
import com.swayy.shared.domain.model.ClubItem
import com.swayy.shared.domain.model.ClubItemResponse
import com.swayy.shared.domain.model.Favorite

fun ClubItemResponse.toEntity(): ClubsEntity {
    return ClubsEntity(
        0, name, imageUrl, websiteUrl
    )
}

fun ClubsEntity.toDomain(): ClubItem {
    return ClubItem(
        name, imageUrl, websiteUrl
    )
}

internal fun FavoriteEntity.toFavoriteDomain(): Favorite {
    return Favorite(
        id, name, imageUrl, websiteUrl, isFavorite
    )
}

internal fun Favorite.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        id, name, imageUrl, websiteUrl, isFavorite
    )
}