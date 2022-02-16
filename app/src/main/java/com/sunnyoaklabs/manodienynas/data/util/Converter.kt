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
    private val jsonFormatter: JsonFormatter
) {

    fun toParentMeetingFilesJson(list: List<ParentMeetingFile>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<ParentMeetingFile>>(){}.type
        ) ?: "[]"
    }

    fun fromParentMeetingFilesJson(json: String): List<ParentMeetingFile> {
        return jsonParser.fromJson<ArrayList<ParentMeetingFile>>(
            json,
            object : TypeToken<ArrayList<ParentMeetingFile>>(){}.type
        ) ?: emptyList()
    }

    fun toMessageFilesJson(list: List<MessageFile>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<MessageFile>>(){}.type
        ) ?: "[]"
    }

    fun fromMessageFilesJson(json: String): List<MessageFile> {
        return jsonParser.fromJson<ArrayList<MessageFile>>(
            json,
            object : TypeToken<ArrayList<MessageFile>>(){}.type
        ) ?: emptyList()
    }

    fun toStringListJson(list: List<String>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: "[]"
    }

    fun fromStringListJson(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: emptyList()
    }

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

    fun toMessagesIndividual(html: String): MessageIndividual {
        return webScrapper.toMessagesIndividual(html = html)
    }

    fun toHoliday(html: String): List<Holiday> {
        return webScrapper.toHoliday(html = html)
    }

    fun toParentMeeting(html: String): List<ParentMeeting> {
        return webScrapper.toParentMeetings(html = html)
    }

    fun toSchedule(html: String): List<Schedule> {
        return webScrapper.toSchedule(html = html)
    }

    fun toCalendar(json: String): List<Calendar> {
        return jsonFormatter.toCalendar(json = json)
    }

}
