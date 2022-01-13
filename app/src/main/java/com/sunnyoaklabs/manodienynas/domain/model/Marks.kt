package com.sunnyoaklabs.manodienynas.domain.model

import com.sunnyoaklabs.manodienynas.domain.model.nested.MarkEvent

data class Marks(
    val lesson: String,
    val teacher: String,
    val average: String,
    val markEvent: List<MarkEvent>
)