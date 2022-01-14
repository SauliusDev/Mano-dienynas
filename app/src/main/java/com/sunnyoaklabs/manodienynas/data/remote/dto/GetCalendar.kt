package com.sunnyoaklabs.manodienynas.data.remote.dto

data class GetCalendar(
    val dateFrom: String,
    val dateTo: String,
    val changeDate: String,
    val orderBy: Int,
    val lessonSelect: Int
)
