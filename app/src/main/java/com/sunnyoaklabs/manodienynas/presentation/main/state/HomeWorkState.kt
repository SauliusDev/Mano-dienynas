package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.HomeWork

data class HomeWorkState(
    val homeWork: List<HomeWork> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingLocale: Boolean = true,
    val page: Int = 1,
    val isEveryHomeWorkLoaded: Boolean = false
)
