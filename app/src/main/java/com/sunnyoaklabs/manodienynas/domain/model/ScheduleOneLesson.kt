package com.sunnyoaklabs.manodienynas.domain.model

data class ScheduleOneLesson(
    val weekDay: Long,
    val timeRange: String,
    val lessonOrder: Long,
    val lesson: String,
    val id: Long? = null
)
