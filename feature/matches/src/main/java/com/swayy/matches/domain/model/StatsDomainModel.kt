package com.swayy.matches.domain.model

import com.swayy.core_network.model.stats.Statistic
import com.swayy.core_network.model.stats.Team

data class StatsDomainModel(
    val statistics: List<Statistic>,
    val team: Team
)
