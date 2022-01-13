package com.sunnyoaklabs.manodienynas.data.util

interface JsonFormatter {

    fun <T> fromMarksJson(json: String): List<EventDto>
}