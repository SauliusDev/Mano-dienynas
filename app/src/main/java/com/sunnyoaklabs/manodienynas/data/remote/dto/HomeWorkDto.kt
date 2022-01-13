package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.HomeWork

data class HomeWorkDto(
    val month: String,
    val monthDay: String,
    val weekDay: String,
    val lesson: String,
    val teacher: String,
    val description: String,
    val dateAddition: String,
    val attachmentsUrl: String
) {
    fun toHomeWork(): HomeWork {
        return HomeWork(
            month = month,
            monthDay = monthDay,
            weekDay = weekDay,
            lesson = lesson,
            teacher = teacher,
            description = description,
            dateAddition = dateAddition,
            attachmentsUrl = attachmentsUrl
        )
    }
}