package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.ClassWork
import com.sunnyoaklabs.manodienynas.domain.model.Event

data class ClassWorkState(
    val classWork: List<ClassWork> = emptyList(),
    val isLoading: Boolean = true
)
