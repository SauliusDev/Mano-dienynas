package com.sunnyoaklabs.manodienynas.presentation.main.state

data class MoreFragmentTypeState(
    val scheduleIsSelected: Boolean = true,
    val calendarIsSelected: Boolean = false,
    val holidayIsSelected: Boolean = false,
    val parentMeetingsIsSelected: Boolean = false
)