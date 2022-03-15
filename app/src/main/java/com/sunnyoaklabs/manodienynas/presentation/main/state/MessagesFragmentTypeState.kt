package com.sunnyoaklabs.manodienynas.presentation.main.state

data class MessagesFragmentTypeState(
    val gottenIsSelected: Boolean = true,
    val sentIsSelected: Boolean = false,
    val starredIsSelected: Boolean = false,
    val deletedIsSelected: Boolean = false
)