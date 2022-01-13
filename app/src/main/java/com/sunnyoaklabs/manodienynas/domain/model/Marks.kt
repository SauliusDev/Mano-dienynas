package com.sunnyoaklabs.manodienynas.domain.model

data class Marks(
    val lesson: String,
    val teacher: String,
    val average: String,
    val markEvent: List<MarkEvent>
)