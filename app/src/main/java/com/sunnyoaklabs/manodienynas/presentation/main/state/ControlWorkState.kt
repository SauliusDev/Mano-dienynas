package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.ControlWork
import com.sunnyoaklabs.manodienynas.domain.model.Event

data class ControlWorkState(
    val controlWork: List<ControlWork> = emptyList(),
    val isLoading: Boolean = false
)
