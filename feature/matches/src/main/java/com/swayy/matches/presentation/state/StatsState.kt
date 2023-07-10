package com.swayy.matches.presentation.state

import com.swayy.matches.domain.model.StatsDomainModel

data class StatsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val stats: List<StatsDomainModel> = emptyList()
)
