package com.swayy.core_network.model.standings

data class Home(
    val draw: Int,
    val goals: Goals,
    val lose: Int,
    val played: Int,
    val win: Int
)