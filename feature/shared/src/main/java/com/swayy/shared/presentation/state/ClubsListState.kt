package com.swayy.shared.presentation.state

import com.swayy.shared.domain.model.ClubItem

data class ClubsListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val clubs: List<ClubItem> = emptyList()
)
