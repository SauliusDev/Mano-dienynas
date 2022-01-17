package com.sunnyoaklabs.manodienynas.core.util

fun Boolean.toLong(): Long = if (this) 1 else 0

fun Int.toBoolean() = this == 1