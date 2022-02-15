package com.sunnyoaklabs.manodienynas.domain.model

data class Calendar(
    val allDay: Boolean,
    val type: String,
    val start: String,
    val title: String,
    val id: Long? = null
)

