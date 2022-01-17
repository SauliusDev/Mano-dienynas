package com.sunnyoaklabs.manodienynas.domain.model

data class Event(
    val id: Long,
    val title: String,
    val pupilInfo: String,
    val createDate: String,
    val createDateText: String,
    val eventHeader: String,
    val eventText: String,
    val creator_name: String,
)
