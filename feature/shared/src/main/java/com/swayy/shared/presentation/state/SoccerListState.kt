package com.swayy.shared.presentation.state

import com.swayy.shared.domain.model.ClubItem
import com.swayy.shared.domain.model.Soccer

data class SoccerListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val soccer: List<Soccer> = emptyList()
)
