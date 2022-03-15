package com.sunnyoaklabs.manodienynas.presentation.main.state

data class MarkFragmentTypeState(
    val markTypeIsSelected: Boolean = true,
    val controlWorkTypeIsSelected: Boolean = false,
    val homeWorkTypeIsSelected: Boolean = false,
    val classWorkTypeIsSelected: Boolean = false
)