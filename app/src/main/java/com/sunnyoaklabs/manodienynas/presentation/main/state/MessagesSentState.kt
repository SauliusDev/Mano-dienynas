package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.Message

data class MessagesSentState(
    val messagesSent: List<Message> = emptyList(),
    val page: Int = 1,
    val isLoading: Boolean = false
)
