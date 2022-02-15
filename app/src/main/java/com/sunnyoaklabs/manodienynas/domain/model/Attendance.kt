package com.sunnyoaklabs.manodienynas.domain.model

data class Attendance(
    val lessonTitle: String,
    val teacher: String,
    val attendance: List<Int>,
    val attendanceRange: List<AttendanceRange>,
    val id: Long? = null
)
