package com.sunnyoaklabs.manodienynas.core.util

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun String.toDocument(): Document = Jsoup.parse(this)

fun Boolean.toLong(): Long = if (this) 1 else 0

fun Long.toBoolean() = this.toInt() == 1