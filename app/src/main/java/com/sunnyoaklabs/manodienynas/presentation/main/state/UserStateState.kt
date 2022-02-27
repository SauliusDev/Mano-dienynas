package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.presentation.main.UserState

data class UserStateState(
    val userState: UserState? = null,
    val isLoading: Boolean = false
)
