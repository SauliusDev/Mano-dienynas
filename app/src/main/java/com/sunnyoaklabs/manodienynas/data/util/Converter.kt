package com.sunnyoaklabs.manodienynas.data.util

import com.google.gson.reflect.TypeToken
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostLogin
import com.sunnyoaklabs.manodienynas.domain.model.*

class Converter(
    private val webScrapper: WebScrapper,
    private val jsonParser: JsonParser,
) {

    fun toAttendanceJson(list: List<Int>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<Int>>(){}.type
        ) ?: "[]"
    }

    fun fromAttendanceJson(json: String): List<Int> {
        return jsonParser.fromJson<ArrayList<Int>>(
            json,
            object : TypeToken<ArrayList<Int>>(){}.type
        ) ?: emptyList()
    }

    fun toAttendanceRangeJson(list: List<AttendanceRange>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<AttendanceRange>>(){}.type
        ) ?: "[]"
    }

    fun fromAttendanceRangeJson(json: String): List<AttendanceRange> {
        return jsonParser.fromJson<ArrayList<AttendanceRange>>(
            json,
            object : TypeToken<ArrayList<AttendanceRange>>(){}.type
        ) ?: emptyList()
    }

    fun toMarkEventJson(list: List<MarkEvent>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<MarkEvent>>(){}.type
        ) ?: "[]"
    }

    fun fromMarkEventJson(json: String): List<MarkEvent> {
        return jsonParser.fromJson<ArrayList<MarkEvent>>(
            json,
            object : TypeToken<ArrayList<MarkEvent>>(){}.type
        ) ?: emptyList()
    }

    fun toPostLogin(payload: PostLogin): String {
        return jsonParser.toJson(
            payload,
            object : TypeToken<PostLogin>(){}.type
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

    fun toUser(html: String): User {
        return webScrapper.toUser(html = html)
    }

    fun toEvents(html: String): List<Event> {
        return webScrapper.toEvents(html = html)
    }

    fun toMarks(html: String): List<Mark> {
        return webScrapper.toMarks(html = html)
    }

    fun toAttendance(html: String): List<Attendance> {
        return webScrapper.toAttendance(html = html)
    }

    fun toClassWork(html: String): List<ClassWork> {
        return webScrapper.toClassWork(html = html)
    }

    fun toHomeWork(html: String): List<HomeWork> {
        return webScrapper.toHomeWork(html = html)
    }

    fun toControlWork(html: String): List<ControlWork> {
        return webScrapper.toControlWork(html = html)
    }

    fun toTerm(html: String): List<Term> {
        return webScrapper.toTerm(html = html)
    }

    fun toMessages(html: String): List<Message> {
        return webScrapper.toMessages(html = html)
    }

    fun toMessagesIndividual(html: String): List<MessageIndividual> {
        return webScrapper.toMessagesIndividual(html = html)
    }

    fun toHoliday(html: String): List<Holiday> {
        return webScrapper.toHoliday(html = html)
    }

    fun toParentMeeting(html: String): List<ParentMeeting> {
        return webScrapper.toParentMeeting(html = html)
    }

    fun toSchedule(html: String): List<Schedule> {
        return webScrapper.toSchedule(html = html)
    }

    fun toCalendar(html: String): List<Calendar> {
        return webScrapper.toCalendar(html = html)
    }

}
