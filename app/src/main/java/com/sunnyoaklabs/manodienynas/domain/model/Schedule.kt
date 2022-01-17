package com.sunnyoaklabs.manodienynas.domain.model

data class Schedule(
    val id: Long,
    val timeRange: String,
    val lessonOrder: Long,
    val lesson: String,
)
