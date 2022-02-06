package com.sunnyoaklabs.manodienynas.data.remote.dto

data class PostLogin(
    val username: String,
    val password: String,
    val dienynas_remember_me: Int
)
