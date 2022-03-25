package com.sunnyoaklabs.manodienynas.data.util

import com.sunnyoaklabs.manodienynas.domain.model.*
import manodienynas.db.*

interface DataSourceObjectParser {

    fun toPersonFromEntity(personEntity: PersonEntity?): Person

    fun toSettingsFromEntity(settingsEntity: SettingsEntity?): Settings

    fun toCredentialsFromEntity(credentialsEntity: CredentialsEntity?): Credentials

    fun toEventFromEntity(eventEntity: EventEntity?): Event

    fun toMarkFromEntity(markEntity: MarkEntity?): Mark

    fun toAttendanceFromEntity(attendanceEntity: AttendanceEntity?): Attendance

    fun toClassWorkFromEntity(classworkEntity: ClassworkEntity?): ClassWork

    fun toHomeWorkFromEntity(homeworkEntity: HomeworkEntity?): HomeWork

    fun toControlWorkFromEntity(controlWorkEntity: ControlWorkEntity?): ControlWork

    fun toTermFromEntity(termEntity: TermEntity?): Term

    fun toTermMarkDialogItemFromEntity(termMarkDialogEntity: TermMarkDialogEntity?): TermMarkDialogItem

    fun toMessageGottenFromEntity(messageGottenEntity: MessageGottenEntity?): Message

    fun toMessageSentFromEntity(messageSentEntity: MessageSentEntity?): Message

    fun toMessageStarredFromEntity(messageStartedEntity: MessageStartedEntity?): Message

    fun toMessageDeletedFromEntity(messageDeletedEntity: MessageDeletedEntity?): Message

    fun toMessageIndividualFromEntity(messageIndividualEntity: MessageIndividualEntity?): MessageIndividual

    fun toHolidayFromEntity(holidayEntity: HolidayEntity?): Holiday

    fun toParentMeetingFromEntity(parentMeetingEntity: ParentMeetingEntity?): ParentMeeting

    fun toScheduleFromEntity(scheduleEntity: ScheduleEntity?): ScheduleOneLesson

    fun toCalendarFromEntity(calendarEntity: CalendarEntity?): Calendar

    fun toCalendarEventFromEntity(calendarEventEntity: CalendarEventEntity?): CalendarEvent

}