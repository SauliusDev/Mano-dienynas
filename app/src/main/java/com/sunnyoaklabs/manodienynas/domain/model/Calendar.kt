package com.sunnyoaklabs.manodienynas.domain.model

data class Calendar(
    val title: String,
    val start: String,
    val url: String,
    val type: String,
    val allDay: Boolean,
    val id: Long? = null
)

