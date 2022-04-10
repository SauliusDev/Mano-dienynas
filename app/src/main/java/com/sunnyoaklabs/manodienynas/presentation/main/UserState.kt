package com.sunnyoaklabs.manodienynas.presentation.main

data class UserState(
    val isUserLoggedIn: Boolean = false,
    val isSessionGotten: Boolean = false,
    val triedGettingSession: Boolean = false
)