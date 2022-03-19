package com.sunnyoaklabs.manodienynas.data.util

import com.google.gson.reflect.TypeToken
import com.sunnyoaklabs.manodienynas.domain.model.*
import manodienynas.db.*

class Converter(
    private val webScrapper: WebScrapper,
    private val jsonParser: JsonParser,
    private val jsonFormatter: JsonFormatter,
    private val dataSourceObjectParser: DataSourceObjectParser
) {

    fun toSchoolInfoJson(schoolInfo: SchoolInfo?): String {
        return jsonParser.toJson(
            schoolInfo,
            object : TypeToken<SchoolInfo>(){}.type
        ) ?: "[]"
    }

    fun toSchoolNamesJson(list: List<SchoolInfo>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<SchoolInfo>>(){}.type
        ) ?: "[]"
    }

    fun toTermRangeJson(list: List<TermRange>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<TermRange>>(){}.type
        ) ?: "[]"
    }

    fun toParentMeetingFilesJson(list: List<ParentMeetingFile>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<ParentMeetingFile>>(){}.type
        ) ?: "[]"
    }

    fun toMessageFilesJson(list: List<MessageFile>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<MessageFile>>(){}.type
        ) ?: "[]"
    }

    fun toStringListJson(list: List<String>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: "[]"
    }

    fun toAttendanceJson(list: List<Int>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<Int>>(){}.type
        ) ?: "[]"
    }

    fun toAttendanceRangeJson(list: List<AttendanceRange>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<AttendanceRange>>(){}.type
        ) ?: "[]"
    }

    fun toMarkEventJson(list: List<MarkEvent>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<MarkEvent>>(){}.type
        ) ?: "[]"
    }

    fun toPerson(html: String): Person {
        return webScrapper.toPerson(html = html)
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

    fun toMarkEventItem(html: String): MarksEventItem {
        return webScrapper.toMarkEventItem(html = html)
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

    fun toTermMarkDialogItem(html: String, url: String): TermMarkDialogItem {
        return webScrapper.toTermMarkDialogItem(html = html, url = url)
    }

    fun toMessages(html: String): List<Message> {
        return webScrapper.toMessages(html = html)
    }

    fun toMessagesIndividual(html: String): MessageIndividual {
        return webScrapper.toMessagesIndividual(html = html)
    }

    fun toMessagesIndividualSent(html: String): MessageIndividual {
        return webScrapper.toMessagesIndividualSent(html = html)
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

    fun toCalendarEvent(html: String): CalendarEvent {
        return webScrapper.toCalendarEvent(html = html)
    }

    fun toPersonFromEntity(personEntity: PersonEntity?): Person {
        return dataSourceObjectParser.toPersonFromEntity(personEntity)
    }

    fun toSettingsFromEntity(settingsEntity: SettingsEntity?): Settings {
        return dataSourceObjectParser.toSettingsFromEntity(settingsEntity)
    }

    fun toCredentialsFromEntity(credentialsEntity: CredentialsEntity?): Credentials {
        return dataSourceObjectParser.toCredentialsFromEntity(credentialsEntity)
    }

    fun toEventFromEntity(eventEntity: EventEntity?): Event {
        return dataSourceObjectParser.toEventFromEntity(eventEntity)
    }

    fun toMarkFromEntity(markEntity: MarkEntity?): Mark {
        return dataSourceObjectParser.toMarkFromEntity(markEntity)
    }

    fun toAttendanceFromEntity(attendanceEntity: AttendanceEntity?): Attendance {
        return dataSourceObjectParser.toAttendanceFromEntity(attendanceEntity)
    }

    fun toClassWorkFromEntity(classworkEntity: ClassworkEntity?): ClassWork {
        return dataSourceObjectParser.toClassWorkFromEntity(classworkEntity)
    }

    fun toHomeWorkFromEntity(homeworkEntity: HomeworkEntity?): HomeWork {
        return dataSourceObjectParser.toHomeWorkFromEntity(homeworkEntity)
    }

    fun toControlWorkFromEntity(controlWorkEntity: ControlWorkEntity?): ControlWork {
        return dataSourceObjectParser.toControlWorkFromEntity(controlWorkEntity)
    }

    fun toTermFromEntity(termEntity: TermEntity?): Term {
        return dataSourceObjectParser.toTermFromEntity(termEntity)
    }

    fun toTermMarkDialogItemFromEntity(termMarkDialogEntity: TermMarkDialogEntity?): TermMarkDialogItem {
        return dataSourceObjectParser.toTermMarkDialogItemFromEntity(termMarkDialogEntity)
    }

    fun toMessageGottenFromEntity(messageGottenEntity: MessageGottenEntity?): Message {
        return dataSourceObjectParser.toMessageGottenFromEntity(messageGottenEntity)
    }

    fun toMessageSentFromEntity(messageSentEntity: MessageSentEntity?): Message {
        return dataSourceObjectParser.toMessageSentFromEntity(messageSentEntity)
    }

    fun toMessageStarredFromEntity(messageStartedEntity: MessageStartedEntity?): Message {
        return dataSourceObjectParser.toMessageStarredFromEntity(messageStartedEntity)
    }

    fun toMessageDeletedFromEntity(messageDeletedEntity: MessageDeletedEntity?): Message {
        return dataSourceObjectParser.toMessageDeletedFromEntity(messageDeletedEntity)
    }

    fun toMessageIndividualFromEntity(messageIndividualEntity: MessageIndividualEntity?): MessageIndividual {
        return dataSourceObjectParser.toMessageIndividualFromEntity(messageIndividualEntity)
    }

    fun toHolidayFromEntity(holidayEntity: HolidayEntity?): Holiday {
        return dataSourceObjectParser.toHolidayFromEntity(holidayEntity)
    }

    fun toParentMeetingFromEntity(parentMeetingEntity: ParentMeetingEntity?): ParentMeeting {
        return dataSourceObjectParser.toParentMeetingFromEntity(parentMeetingEntity)
    }

    fun toScheduleFromEntity(scheduleEntity: ScheduleEntity?): Schedule {
        return dataSourceObjectParser.toScheduleFromEntity(scheduleEntity)
    }

    fun toCalendarFromEntity(calendarEntity: CalendarEntity?): Calendar {
        return dataSourceObjectParser.toCalendarFromEntity(calendarEntity)
    }

    fun toCalendarEventFromEntity(calendarEventEntity: CalendarEventEntity?): CalendarEvent {
        return dataSourceObjectParser.toCalendarEventFromEntity(calendarEventEntity)
    }

}
