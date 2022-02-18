package com.sunnyoaklabs.manodienynas.presentation.main.state

import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.Message

data class MessagesStarredState(
    val messagesStarred: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
