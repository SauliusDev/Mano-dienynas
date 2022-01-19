package com.sunnyoaklabs.manodienynas.core.util

import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import manodienynas.db.CredentialsEntity
import manodienynas.db.SessionIdEntity

fun Boolean.toLong(): Long = if (this) 1 else 0

fun Int.toBoolean() = this == 1

fun CredentialsEntity?.toCredentials(): Credentials {
    return this?.let {
        Credentials(
            this.username ?: "",
            this.password ?: ""
        )
    } ?: Credentials("", "")
}

fun SessionIdEntity?.toSessionId(): String {
    return this?.let {
        this.sessionId ?: ""
    } ?: ""
}