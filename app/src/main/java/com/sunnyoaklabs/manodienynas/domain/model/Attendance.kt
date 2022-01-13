package com.sunnyoaklabs.manodienynas.domain.model

import com.sunnyoaklabs.manodienynas.domain.model.nested.AttendanceRange

data class Attendance(
    val lessonTitle: String,
    val teacher: String,
    val attendance: List<List<Int>>,
    val attendanceRange: List<AttendanceRange>
)
