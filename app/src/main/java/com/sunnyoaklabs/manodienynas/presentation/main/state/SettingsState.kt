package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Settings

data class SettingsState(
    val settings: Settings? = null,
    val isLoading: Boolean = false
)