package com.sunnyoaklabs.manodienynas.data.util

import android.media.metrics.Event
import com.sunnyoaklabs.manodienynas.domain.model.*
import org.jsoup.nodes.Document

interface JsonFormatter {

    fun toEvents(html: Document): List<Event>

    fun toMarks(html: Document): List<Mark>

    fun toAttendance(html: Document): List<Attendance>

    fun toClassWork(html: Document): List<ClassWork>

    fun toHomeWork(html: Document): List<HomeWork>

    fun toControlWork(html: Document): List<ControlWork>

    fun toTerm(html: Document): List<Term>

    fun toTermLegend(html: Document): TermLegend

    fun toMessages(html: Document): List<Message>

    fun toMessagesIndividual(html: Document): List<MessageIndividual>

    fun toHoliday(html: Document): List<Holiday>

    fun toParentMeeting(html: Document): List<ParentMeeting>

    fun toSchedule(html: Document): List<Schedule>

    fun toCalendar(html: Document): List<Calendar>

}