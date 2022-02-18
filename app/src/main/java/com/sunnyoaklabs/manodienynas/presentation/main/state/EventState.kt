package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Event

data class EventState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false
)
