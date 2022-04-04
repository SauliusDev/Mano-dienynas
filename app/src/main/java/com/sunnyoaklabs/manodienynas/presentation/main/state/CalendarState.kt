package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Calendar
import com.sunnyoaklabs.manodienynas.domain.model.Event

data class CalendarState(
    val calendar: List<Calendar> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingLocale: Boolean = true
)
