package com.sunnyoaklabs.manodienynas.domain.model

data class ParentMeeting(
    val className: String,
    val description: String,
    val date: String,
    val location: String,
    val attachmentUrl: String,
    val creationDate: String,
    val id: Long? = null
)
