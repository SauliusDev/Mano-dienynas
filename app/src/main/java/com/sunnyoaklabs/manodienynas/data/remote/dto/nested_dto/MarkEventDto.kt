package com.sunnyoaklabs.manodienynas.data.remote.dto.nested_dto

import com.sunnyoaklabs.manodienynas.domain.model.nested.MarkEvent

data class MarkEventDto(
    val date: String,
    val marks: String
) {
    fun toMarkEvent(): MarkEvent {
        return MarkEvent(
            date = date,
            marks = marks
        )
    }
}
