package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Calendar
import com.sunnyoaklabs.manodienynas.domain.model.CalendarEvent
import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.TermMarkDialogItem

data class TermMarkDialogItemState(
    val termMarkDialogItem: TermMarkDialogItem? = null,
    val isLoading: Boolean = false
)
