package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Calendar
import com.sunnyoaklabs.manodienynas.domain.model.CalendarEvent
import com.sunnyoaklabs.manodienynas.domain.model.Event

data class CalendarEventState(
    val calendar: CalendarEvent? = null,
    val isLoading: Boolean = false,
    val isLoadingLocale: Boolean = true
)
