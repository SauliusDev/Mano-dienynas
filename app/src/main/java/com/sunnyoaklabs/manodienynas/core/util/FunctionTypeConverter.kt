package com.sunnyoaklabs.manodienynas.core.util

import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.model.Mark
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import manodienynas.db.CredentialsEntity
import manodienynas.db.MarkEntity
import manodienynas.db.SessionIdEntity
import manodienynas.db.SettingsEntity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun String.toDocument(): Document = Jsoup.parse(this)

fun Boolean.toLong(): Long = if (this) 1 else 0

fun Long.toBoolean() = this.toInt() == 1