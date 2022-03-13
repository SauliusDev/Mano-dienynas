package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.Mark

data class MarkState(
    val marks: List<Mark> = emptyList(),
    val isLoading: Boolean = true
)
