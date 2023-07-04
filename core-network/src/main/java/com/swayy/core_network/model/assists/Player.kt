package com.swayy.core_network.model.assists

data class Player(
    val age: Int,
    val birth: com.swayy.core_network.model.assists.Birth,
    val firstname: String,
    val height: String,
    val id: Int,
    val injured: Boolean,
    val lastname: String,
    val name: String,
    val nationality: String,
    val photo: String,
    val weight: String
)