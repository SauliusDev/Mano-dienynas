package com.sunnyoaklabs.manodienynas.domain.model

data class Attendance(
    val id: Long,
    val lessonTitle: String,
    val teacher: String,
    val attendance: List<AttendanceItem>,
    val attendanceRange: List<AttendanceRange>
)
