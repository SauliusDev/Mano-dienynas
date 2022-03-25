package com.sunnyoaklabs.manodienynas.data.util

import com.sunnyoaklabs.manodienynas.domain.model.*

interface WebScrapper {

    fun toPerson(html: String): Person

    fun toEvents(html: String): List<Event>

    fun toMarks(html: String): List<Mark>

    fun toMarkEventItem(html: String): MarksEventItem

    fun toAttendance(html: String): List<Attendance>

    fun toClassWork(html: String): List<ClassWork>

    fun toHomeWork(html: String): List<HomeWork>

    fun toControlWork(html: String): List<ControlWork>

    fun toTerm(html: String): List<Term>

    fun toTermMarkDialogItem(html: String, url: String): TermMarkDialogItem

    fun toMessages(html: String): List<Message>

    fun toMessagesIndividual(html: String): MessageIndividual

    fun toMessagesIndividualSent(html: String): MessageIndividual

    fun toHoliday(html: String): List<Holiday>

    fun toParentMeetings(html: String): List<ParentMeeting>

    fun toSchedule(html: String): List<ScheduleOneLesson>

    fun toCalendarEvent(html: String): CalendarEvent

}