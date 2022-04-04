package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Person

data class PersonState(
    val person: Person? = null,
    val isLoading: Boolean = false,
    val isLoadingLocale: Boolean = true
)
