package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.Holiday

data class HolidayState(
    val holiday: List<Holiday> = emptyList(),
    val isLoading: Boolean = false
)
