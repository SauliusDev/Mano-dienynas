package com.sunnyoaklabs.manodienynas.domain.model

data class MessageIndividual(
    val id: Long,
    val messageId: String,
    val title: String ,
    val sender: String,
    val date: String,
    val content: String
)
