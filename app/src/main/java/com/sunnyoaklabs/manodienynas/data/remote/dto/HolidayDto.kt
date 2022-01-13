package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.Holiday

data class HolidayDto(
    val name: String,
    val rangeStart: String,
    val rangeEnd: String
) {
    fun toHoliday(): Holiday {
        return Holiday(
            name = name,
            rangeStart = rangeStart,
            rangeEnd = rangeEnd
        )
    }
}
