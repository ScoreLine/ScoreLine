package com.swayy.shared.data.mapper

import com.swayy.core_database.model.ClubsEntity
import com.swayy.shared.domain.model.ClubItem
import com.swayy.shared.domain.model.ClubItemResponse

fun ClubItemResponse.toEntity():ClubsEntity{
    return ClubsEntity(
        0,name, imageUrl, websiteUrl
    )
}

fun ClubsEntity.toDomain():ClubItem{
    return ClubItem(
        name, imageUrl, websiteUrl
    )
}