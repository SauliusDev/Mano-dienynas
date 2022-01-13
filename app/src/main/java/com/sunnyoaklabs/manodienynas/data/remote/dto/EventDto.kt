package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.Attendance
import com.sunnyoaklabs.manodienynas.domain.model.Event

data class EventDto(
    val title: String,
    val pupilInfo: String,
    val createDate: String,
    val createDateText: String?,
    val eventHeader: String,
    val eventText: String,
    val creator_name: String,
) {
    fun toEvent(): Event {
        return Event(
            title = title,
            pupilInfo = pupilInfo,
            createDate = createDate,
            createDateText = createDateText,
            eventHeader = eventHeader,
            eventText = eventText,
            creator_name = creator_name
        )
    }
}
