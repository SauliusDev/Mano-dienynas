package com.sunnyoaklabs.manodienynas.data.util

import com.sunnyoaklabs.manodienynas.domain.model.*

interface WebScrapper {

    fun toUser(html: String): User

    fun toEvents(html: String): List<Event>

    fun toMarks(html: String): List<Mark>

    fun toAttendance(html: String): List<Attendance>

    fun toClassWork(html: String): List<ClassWork>

    fun toHomeWork(html: String): List<HomeWork>

    fun toControlWork(html: String): List<ControlWork>

    fun toTerm(html: String): List<Term>

    fun toMessages(html: String): List<Message>

    fun toMessagesIndividual(html: String): List<MessageIndividual>

    fun toHoliday(html: String): List<Holiday>

    fun toParentMeeting(html: String): List<ParentMeeting>

    fun toSchedule(html: String): List<Schedule>

    fun toCalendar(html: String): List<Calendar>

}