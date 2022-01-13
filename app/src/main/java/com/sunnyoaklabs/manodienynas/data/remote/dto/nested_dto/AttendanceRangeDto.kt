package com.sunnyoaklabs.manodienynas.data.remote.dto.nested_dto

import com.sunnyoaklabs.manodienynas.domain.model.nested.AttendanceRange

data class AttendanceRangeDto(
    val title: String,
    val date: String?
) {
    fun toAttendanceRange(): AttendanceRange {
        return AttendanceRange(
            title = title,
            date = date
        )
    }
}
