package com.swayy.core.data

import com.swayy.core.domain.model.WebMatch

data class WebMatchState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val matches: List<WebMatch> = emptyList()
)
