package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.Calendar

data class CalendarDto(
    val allDay: Boolean,
    val type: String,
    val start: String,
    val title: String
) {
    fun toCalendar(): Calendar {
        return Calendar(
            allDay = allDay,
            type = type,
            start = start,
            title = title
        )
    }
}

