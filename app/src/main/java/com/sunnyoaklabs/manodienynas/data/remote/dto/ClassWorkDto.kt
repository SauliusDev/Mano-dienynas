package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.ClassWork

data class ClassWorkDto(
    val month: String,
    val monthDay: String,
    val weekDay: String,
    val lesson: String,
    val teacher: String,
    val description: String,
    val dateAddition: String
) {
    fun toClassWork(): ClassWork {
        return ClassWork(
            month = month,
            monthDay = monthDay,
            weekDay = weekDay,
            lesson = lesson,
            teacher = teacher,
            description = description,
            dateAddition = dateAddition
        )
    }
}
