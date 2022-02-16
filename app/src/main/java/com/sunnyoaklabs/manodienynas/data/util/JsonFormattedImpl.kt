package com.sunnyoaklabs.manodienynas.data.util

import android.util.Log
import com.sunnyoaklabs.manodienynas.domain.model.Calendar
import org.json.JSONArray
import org.jsoup.Jsoup

class JsonFormattedImpl : JsonFormatter {

    override fun toCalendar(json: String): List<Calendar> {
        val calendarList: MutableList<Calendar> = mutableListOf()
        val jsonArrayOfEvents = JSONArray(json)
        for (i in 0 until jsonArrayOfEvents.length()) {
            val jsonObjectEvent = jsonArrayOfEvents.getJSONObject(i)
            val url = jsonObjectEvent
                .getString("url")
                .removePrefix("javascript:openModal(")
                .removePrefix(")")
                .replace(",", "/")
            calendarList.add(
                Calendar(
                    title = jsonObjectEvent.getString("title"),
                    start = jsonObjectEvent.getString("start"),
                    url = url,
                    type = jsonObjectEvent.getString("className"),
                    allDay = jsonObjectEvent.getBoolean("allDay")
                )
            )
        }
        return calendarList
    }

}