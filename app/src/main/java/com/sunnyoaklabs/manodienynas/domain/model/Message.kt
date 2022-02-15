package com.sunnyoaklabs.manodienynas.domain.model

data class Message(
    val messageId: String,
    val isStarred: String,
    val date: String,
    val theme: String,
    val sender: String,
    val id: Long? = null
)
