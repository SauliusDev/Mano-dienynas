package com.sunnyoaklabs.manodienynas.domain.model

data class User(
    val name: String,
    val role: String,
    val schoolsNames: List<SchoolInfo>,
)
