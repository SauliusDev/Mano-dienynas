package com.sunnyoaklabs.manodienynas.domain.model

data class Calendar(
    val id: Long,
    val allDay: Boolean,
    val type: String,
    val start: String,
    val title: String
)

