package com.sunnyoaklabs.manodienynas.domain.model

data class MessageIndividual(
    val messageId: String,
    val title: String ,
    val sender: String,
    val date: String,
    val content: String,
    val recipients: String,
    val files: List<MessageFile>,
    val id: Long? = null
)
