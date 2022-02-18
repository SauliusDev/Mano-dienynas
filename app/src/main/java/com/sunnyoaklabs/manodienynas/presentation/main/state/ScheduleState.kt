package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.Schedule

data class ScheduleState(
    val schedule: List<Schedule> = emptyList(),
    val isLoading: Boolean = false
)
