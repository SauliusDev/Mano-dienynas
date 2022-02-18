package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.Message

data class MessagesDeletedState(
    val messagesDeleted: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
