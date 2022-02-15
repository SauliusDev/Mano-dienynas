package com.sunnyoaklabs.manodienynas.domain.model

data class MarkEvent(
    val date: String = "",
    val marks: String,
    val infoUrl: String,
    val id: Long? = null
)
