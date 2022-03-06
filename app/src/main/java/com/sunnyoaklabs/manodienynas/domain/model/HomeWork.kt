package com.sunnyoaklabs.manodienynas.domain.model

data class HomeWork(
    val month: String,
    val monthDay: String,
    val weekDay: String,
    val lesson: String,
    val teacher: String,
    val description: String,
    val dateAddition: String,
    val dueDate: String,
    val attachmentsUrl: String,
    val id: Long? = null
)