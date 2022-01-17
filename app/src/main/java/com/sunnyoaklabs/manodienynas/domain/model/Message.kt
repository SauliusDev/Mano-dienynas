package com.sunnyoaklabs.manodienynas.domain.model

data class Message(
    val id: Long,
    val messageId: String,
    val isStarred: String,
    val date: String,
    val theme: String,
    val sender: String
)
