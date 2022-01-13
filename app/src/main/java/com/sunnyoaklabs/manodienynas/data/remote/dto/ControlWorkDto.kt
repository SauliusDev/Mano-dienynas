package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.ControlWork

data class ControlWorkDto(
    val index: String,
    val date: String,
    val group: String,
    val theme: String,
    val dateAddition: String
) {
    fun toControlWork(): ControlWork {
        return ControlWork(
            index = index,
            date = date,
            group = group,
            theme = theme,
            dateAddition = dateAddition
        )
    }
}