package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Attendance
import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.Mark

data class AttendanceState(
    val attendance: List<Attendance> = emptyList(),
    val isLoading: Boolean = true,
    val isLoadingLocale: Boolean = true
)
