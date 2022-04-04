package com.sunnyoaklabs.manodienynas.core.util

sealed class UIEvent {
    data class ShowSnackbar(val message: String) : UIEvent()
    data class StartActivity(val message: String) : UIEvent()
}