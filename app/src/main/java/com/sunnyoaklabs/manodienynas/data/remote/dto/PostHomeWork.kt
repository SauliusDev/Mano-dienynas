package com.sunnyoaklabs.manodienynas.data.remote.dto

data class PostHomeWork(
    val dateFrom: String,
    val dateTo: String,
    val changeDate: String,
    val orderBy: Int,
    val lessonSelect: Int
)
