package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.ScheduleDay
import com.sunnyoaklabs.manodienynas.domain.model.ScheduleOneLesson

data class ScheduleState(
    val schedule: List<ScheduleDay> = emptyList(),
    val isLoading: Boolean = false
)
