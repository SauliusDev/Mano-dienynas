package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.Schedule

data class ScheduleDto(
    val timeRange: String,
    val lessonOrder: Int,
    val lesson: String,
) {
    fun toSchedule(): Schedule {
        return Schedule(
            timeRange = timeRange,
            lessonOrder = lessonOrder,
            lesson = lesson
        )
    }
}
