package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.Term

data class TermState(
    val terms: List<Term> = emptyList(),
    val isLoading: Boolean = false
)
