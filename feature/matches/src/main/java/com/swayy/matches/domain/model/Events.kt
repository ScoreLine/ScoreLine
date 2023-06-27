package com.swayy.matches.domain.model

import com.swayy.core_network.model.events.Assist
import com.swayy.core_network.model.events.Player
import com.swayy.core_network.model.events.Team
import com.swayy.core_network.model.events.Time

data class Events(
    val id:Int,
    val assist: Assist?,
    val comments: String?,
    val detail: String?,
    val player: Player?,
    val team: Team?,
    val time: Time?,
    val type: String?
)
