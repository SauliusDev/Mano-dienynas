package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Credentials

data class CredentialsState (
    val credentials: Credentials? = null,
    val isLoading: Boolean = false
)