package com.sunnyoaklabs.manodienynas.domain.model

data class ControlWork(
    val index: String,
    val date: String,
    val group: String,
    val theme: String,
    val description: String,
    val dateAddition: String,
    val id: Long? = null
)