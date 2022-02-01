package com.sunnyoaklabs.manodienynas.core.util

import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import manodienynas.db.CredentialsEntity
import manodienynas.db.SessionIdEntity
import manodienynas.db.SettingsEntity

fun Boolean.toLong(): Long = if (this) 1 else 0

fun Long.toBoolean() = this.toInt() == 1

fun SettingsEntity?.toSettings(): Settings {
    return this?.let {
        Settings(
            (this.keepSignedIn ?: 0).toBoolean()
        )
    } ?: Settings(false)
}

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