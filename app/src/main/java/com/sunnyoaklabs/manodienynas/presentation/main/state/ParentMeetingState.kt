package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.ParentMeeting

data class ParentMeetingState(
    val parentMeetings: List<ParentMeeting> = emptyList(),
    val isLoading: Boolean = false
)
