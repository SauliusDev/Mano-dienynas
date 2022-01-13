package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.Message

data class MessageDto(
    val messageId: String,
    val isFavorite: String,
    val date: String,
    val theme: String,
    val sender: String
) {
    fun toMessage(): Message {
        return Message(
            messageId = messageId,
            isFavorite = isFavorite,
            date = date,
            theme = theme,
            sender = sender
        )
    }
}
