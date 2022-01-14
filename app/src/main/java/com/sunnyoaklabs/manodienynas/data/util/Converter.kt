package com.sunnyoaklabs.manodienynas.data.util

import android.media.metrics.Event
import com.google.gson.reflect.TypeToken
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWork
import com.sunnyoaklabs.manodienynas.data.util.JsonFormatter
import com.sunnyoaklabs.manodienynas.data.util.JsonParser
import com.sunnyoaklabs.manodienynas.domain.model.*
import org.jsoup.nodes.Document

class Converter(
    private val jsonFormatter: JsonFormatter,
    private val jsonParser: JsonParser
) {

    fun fromAttendanceJson(json: String): List<List<Int>> {
        return jsonParser.fromJson<ArrayList<ArrayList<Int>>>(
            json,
            object : TypeToken<ArrayList<ArrayList<Int>>>(){}.type
        ) ?: emptyList()
    }

    fun toAttendanceJson(json: PostClassWork): String {
        return jsonParser.toJson(
            json,
            object : TypeToken<ArrayList<ArrayList<Int>>>(){}.type
        ) ?: "[]"
    }

    fun fromAttendanceRangeJson(json: String): List<PostClassWork> {
        return jsonParser.fromJson<ArrayList<PostClassWork>>(
            json,
            object : TypeToken<ArrayList<PostClassWork>>(){}.type
        ) ?: emptyList()
    }

    fun toAttendanceRangeJson(json: PostClassWork): String {
        return jsonParser.toJson(
            json,
            object : TypeToken<ArrayList<PostClassWork>>(){}.type
        ) ?: "[]"
    }

    fun fromMarkEventJson(json: String): List<MarkEvent> {
        return jsonParser.fromJson<ArrayList<MarkEvent>>(
            json,
            object : TypeToken<ArrayList<MarkEvent>>(){}.type
        ) ?: emptyList()
    }

    fun toMarkEventJson(json: PostClassWork): String {
        return jsonParser.toJson(
            json,
            object : TypeToken<ArrayList<MarkEvent>>(){}.type
        ) ?: "[]"
    }

    fun toPostClassWorkJson(payload: PostClassWork): String {
        return jsonParser.toJson(
            payload,
            object : TypeToken<PostClassWork>(){}.type
        ) ?: "[]"
    }

    fun toPostControlWorkJson(payload: PostControlWork): String {
        return jsonParser.toJson(
            payload,
            object : TypeToken<PostControlWork>(){}.type
        ) ?: "[]"
    }

    fun toPostHomeWorkJson(payload: PostHomeWork): String {
        return jsonParser.toJson(
            payload,
            object : TypeToken<PostHomeWork>(){}.type
        ) ?: "[]"
    }

    fun toEvents(html: Document): List<Event> {
        return jsonFormatter.toEvents(html = html)
    }

    fun toMarks(html: Document): List<Marks> {
        return jsonFormatter.toMarks(html = html)
    }

    fun toAttendance(html: Document): List<Attendance> {
        return jsonFormatter.toAttendance(html = html)
    }

    fun toClassWork(html: Document): List<ClassWork> {
        return jsonFormatter.toClassWork(html = html)
    }

    fun toHomeWork(html: Document): List<HomeWork> {
        return jsonFormatter.toHomeWork(html = html)
    }

    fun toControlWork(html: Document): List<ControlWork> {
        return jsonFormatter.toControlWork(html = html)
    }

    fun toTerm(html: Document): List<Term> {
        return jsonFormatter.toTerm(html = html)
    }

    fun toTermLegend(html: Document): TermLegend {
        return jsonFormatter.toTermLegend(html = html)
    }

    fun toMessages(html: Document): List<Message> {
        return jsonFormatter.toMessages(html = html)
    }

    fun toMessagesIndividual(html: Document): List<MessageIndividual> {
        return jsonFormatter.toMessagesIndividual(html = html)
    }

    fun toHoliday(html: Document): List<Holiday> {
        return jsonFormatter.toHoliday(html = html)
    }

    fun toParentMeeting(html: Document): List<ParentMeetings> {
        return jsonFormatter.toParentMeeting(html = html)
    }

    fun toSchedule(html: Document): List<Schedule> {
        return jsonFormatter.toSchedule(html = html)
    }

    fun toCalendar(html: Document): List<Calendar> {
        return jsonFormatter.toCalendar(html = html)
    }

}
