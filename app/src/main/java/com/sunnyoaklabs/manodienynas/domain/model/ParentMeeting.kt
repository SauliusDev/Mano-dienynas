package com.sunnyoaklabs.manodienynas.domain.model

data class ParentMeeting(
    val className: String,
    val description: String,
    val date: String,
    val location: String,
    val attachmentUrls: List<ParentMeetingFile>,
    val creationDate: String,
    val id: Long? = null
)
