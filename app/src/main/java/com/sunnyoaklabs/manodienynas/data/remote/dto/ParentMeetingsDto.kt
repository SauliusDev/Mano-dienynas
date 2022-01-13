package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.ParentMeetings

data class ParentMeetingsDto(
    val className: String,
    val description: String,
    val date: String,
    val location: String,
    val attachmentUrl: String,
    val creationDate: String
) {
    fun toParentMeeting(): ParentMeetings {
        return ParentMeetings(
            className = className,
            description = description,
            date = date,
            location = location,
            attachmentUrl = attachmentUrl,
            creationDate = creationDate
        )
    }
}
