package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.MessageIndividual

data class MessageIndividualState(
    val messageIndividual: MessageIndividual? = null,
    val isLoading: Boolean = false
)
