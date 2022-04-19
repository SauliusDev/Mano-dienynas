package com.sunnyoaklabs.manodienynas.core.util

sealed class UIEvent {
    data class ShowToast(val message: String) : UIEvent()
    data class Error(val message: String) : UIEvent()
    data class StartActivity(val message: String) : UIEvent()
}