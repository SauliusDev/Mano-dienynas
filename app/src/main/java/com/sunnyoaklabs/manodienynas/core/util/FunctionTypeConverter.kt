package com.sunnyoaklabs.manodienynas.core.util

import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.model.UserSettings
import manodienynas.db.CredentialsEntity
import manodienynas.db.SessionIdEntity
import manodienynas.db.UserSettingEntity

fun Boolean.toLong(): Long = if (this) 1 else 0

fun Long.toBoolean() = this.toInt() == 1

fun UserSettingEntity?.toUserSettings(): UserSettings {
    return this?.let {
        UserSettings(
            (this.keepSignedIn ?: 0).toBoolean(),
        )
    } ?: UserSettings(false)
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