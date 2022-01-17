package com.sunnyoaklabs.manodienynas.domain.model

data class Mark(
    val id: Long,
    val lesson: String,
    val teacher: String,
    val average: String,
    val markEvent: List<MarkEvent>
)