package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.data.remote.dto.nested_dto.MarkEventDto
import com.sunnyoaklabs.manodienynas.domain.model.Marks
import com.sunnyoaklabs.manodienynas.domain.model.nested.MarkEvent


data class MarksDto(
    val lesson: String,
    val teacher: String,
    val average: String,
    val markEvent: List<MarkEventDto>
) {
    fun toMarks(): Marks {
        return Marks(
            lesson = lesson,
            teacher = teacher,
            average = average,
            markEvent = markEvent.map { it.toMarkEvent() }
        )
    }
}
