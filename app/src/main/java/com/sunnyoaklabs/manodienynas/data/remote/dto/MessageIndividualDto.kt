package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.MessageIndividual


data class MessageIndividualDto(
    val messageId: String,
    val title: String ,
    val sender: String,
    val date: String,
    val content: String
) {
    fun toMessageIndividual(): MessageIndividual {
        return MessageIndividual(
            messageId = messageId,
            title = title,
            sender = sender,
            date = date,
            content = content
        )
    }
}
