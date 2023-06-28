package com.swayy.matches.presentation.events.state

import com.swayy.matches.domain.model.Events
import com.swayy.matches.domain.model.Lineup

data class EventsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val events: List<Events> = emptyList()
)
