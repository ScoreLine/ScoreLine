package com.swayy.shared.data.mapper

import com.swayy.core_database.model.ClubsEntity
import com.swayy.core_database.model.FavoriteEntity
import com.swayy.core_database.model.SoccerEntity
import com.swayy.shared.domain.model.ClubItem
import com.swayy.shared.domain.model.ClubItemResponse
import com.swayy.shared.domain.model.Favorite
import com.swayy.shared.domain.model.Soccer
import com.swayy.shared.domain.model.SoccerItemResponse

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

fun SoccerItemResponse.toEntity(): SoccerEntity{
    return SoccerEntity(
        0,leagueName, games, teams, logo, flag, link
    )
}

fun SoccerEntity.toDomain():Soccer{
    return Soccer(
        leagueName, games, teams, logo, flag, link
    )
}

internal fun FavoriteEntity.toFavoriteDomain(): Favorite {
    return Favorite(
        id, name, imageUrl, websiteUrl, isFavorite,games, teams,flag
    )
}

internal fun Favorite.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        id, name, imageUrl, websiteUrl, isFavorite,games, teams, flag
    )
}