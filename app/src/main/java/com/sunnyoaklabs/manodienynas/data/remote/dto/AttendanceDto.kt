package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.data.remote.dto.nested_dto.AttendanceRangeDto
import com.sunnyoaklabs.manodienynas.domain.model.nested.AttendanceRange
import com.sunnyoaklabs.manodienynas.domain.model.Attendance

data class AttendanceDto(
    val lessonTitle: String,
    val teacher: String,
    val attendance: List<List<Int>>,
    val attendanceRange: List<AttendanceRangeDto>
) {
    fun toAttendance(): Attendance {
        return Attendance(
            lessonTitle = lessonTitle,
            teacher = teacher,
            attendance = attendance,
            attendanceRange = attendanceRange.map { it.toAttendanceRange() }
        )
    }
}
