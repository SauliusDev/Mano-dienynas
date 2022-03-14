package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.MarksEventItem

data class MarksEventItemState(
    val marksEventItem: MarksEventItem? = null,
    val isLoading: Boolean = false
)
