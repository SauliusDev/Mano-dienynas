package com.sunnyoaklabs.manodienynas.domain.model

data class Person(
    val name: String,
    val role: String,
    val schoolsNames: List<SchoolInfo>,
)
