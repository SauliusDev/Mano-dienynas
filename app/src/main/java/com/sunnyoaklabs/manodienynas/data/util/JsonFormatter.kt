package com.sunnyoaklabs.manodienynas.data.util

import com.sunnyoaklabs.manodienynas.domain.model.Calendar
import org.jsoup.Jsoup

interface JsonFormatter {

    fun toCalendar(json: String): List<Calendar>

}