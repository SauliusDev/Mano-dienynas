package com.sunnyoaklabs.manodienynas.data.util

import android.media.metrics.Event
import com.sunnyoaklabs.manodienynas.domain.model.*
import org.jsoup.nodes.Document

class JsonFormatterImpl(): JsonFormatter {

    override fun toEvents(html: Document): List<Event> {
        TODO("Not yet implemented")
    }

    override fun toMarks(html: Document): List<Marks> {
        TODO("Not yet implemented")
    }

    override fun toAttendance(html: Document): List<Attendance> {
        TODO("Not yet implemented")
    }

    override fun toClassWork(html: Document): List<ClassWork> {
        TODO("Not yet implemented")
    }

    override fun toHomeWork(html: Document): List<HomeWork> {
        TODO("Not yet implemented")
    }

    override fun toControlWork(html: Document): List<ControlWork> {
        TODO("Not yet implemented")
    }

    override fun toTerm(html: Document): List<Term> {
        TODO("Not yet implemented")
    }

    override fun toTermLegend(html: Document): TermLegend {
        TODO("Not yet implemented")
    }

    override fun toMessages(html: Document): List<Message> {
        TODO("Not yet implemented")
    }

    override fun toMessagesIndividual(html: Document): List<MessageIndividual> {
        TODO("Not yet implemented")
    }

    override fun toHoliday(html: Document): List<Holiday> {
        TODO("Not yet implemented")
    }

    override fun toParentMeeting(html: Document): List<ParentMeetings> {
        TODO("Not yet implemented")
    }

    override fun toSchedule(html: Document): List<Schedule> {
        TODO("Not yet implemented")
    }

    override fun toCalendar(html: Document): List<Calendar> {
        TODO("Not yet implemented")
    }

}