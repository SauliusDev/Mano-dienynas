package com.sunnyoaklabs.manodienynas.domain.model

data class Settings(
    val keepSignedIn: Boolean = false,
    val selectedSchool: SchoolInfo? = null,
)
