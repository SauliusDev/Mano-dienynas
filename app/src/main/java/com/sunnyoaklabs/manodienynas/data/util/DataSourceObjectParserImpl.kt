package com.sunnyoaklabs.manodienynas.data.util

import com.google.gson.reflect.TypeToken
import com.sunnyoaklabs.manodienynas.core.util.toBoolean
import com.sunnyoaklabs.manodienynas.domain.model.*
import manodienynas.db.*

class DataSourceObjectParserImpl(
    private val jsonParser: JsonParser
) : DataSourceObjectParser {

    override fun toPersonFromEntity(personEntity: PersonEntity?): Person {
        return Person(
            personEntity?.name ?: "",
            personEntity?.role ?: "",
            fromSchoolNamesJson(personEntity?.schoolsNames ?: "")
        )
    }

    override fun toSettingsFromEntity(settingsEntity: SettingsEntity?): Settings {
        return Settings(
            (settingsEntity?.keepSignedIn ?: 0).toBoolean(),
            fromSchoolInfoJson(settingsEntity?.selectedSchoolInfo ?: "")
        )
    }

    override fun toCredentialsFromEntity(credentialsEntity: CredentialsEntity?): Credentials {
        return Credentials(
            credentialsEntity?.username ?: "",
            credentialsEntity?.password ?: ""
        )
    }

    override fun toEventFromEntity(eventEntity: EventEntity?): Event {
        return Event(
            eventEntity?.event_id ?: "",
            eventEntity?.title ?: "",
            eventEntity?.pupilInfo ?: "",
            eventEntity?.createDate ?: "",
            eventEntity?.createDateText ?: "",
            eventEntity?.eventHeader ?: "",
            eventEntity?.eventText ?: "",
            eventEntity?.creator_name ?: ""
        )
    }

    override fun toMarkFromEntity(markEntity: MarkEntity?): Mark {
        return Mark(
            markEntity?.lesson ?: "",
            markEntity?.teacher ?: "",
            markEntity?.average ?: "",
            fromMarkEventJson(markEntity?.markEvent ?: ""),
        )
    }

    override fun toAttendanceFromEntity(attendanceEntity: AttendanceEntity?): Attendance {
        return Attendance(
            attendanceEntity?.lessonTitle ?: "",
            attendanceEntity?.teacher ?: "",
            fromAttendanceJson(attendanceEntity?.attendance ?: ""),
            fromAttendanceRangeJson(attendanceEntity?.attendanceRange ?: "")
        )
    }

    override fun toClassWorkFromEntity(classworkEntity: ClassworkEntity?): ClassWork {
        return ClassWork(
            classworkEntity?.month ?: "",
            classworkEntity?.monthDay ?: "",
            classworkEntity?.weekDay ?: "",
            classworkEntity?.lesson ?: "",
            classworkEntity?.teacher ?: "",
            classworkEntity?.description ?: "",
            classworkEntity?.dateAddition ?: "",
            classworkEntity?.attachmentsUrl ?: ""
        )
    }

    override fun toHomeWorkFromEntity(homeworkEntity: HomeworkEntity?): HomeWork {
        return HomeWork(
            homeworkEntity?.month ?: "",
            homeworkEntity?.monthDay ?: "",
            homeworkEntity?.weekDay ?: "",
            homeworkEntity?.lesson ?: "",
            homeworkEntity?.teacher ?: "",
            homeworkEntity?.description ?: "",
            homeworkEntity?.dateAddition ?: "",
            homeworkEntity?.dueDate ?: "",
            homeworkEntity?.attachmentsUrl ?: ""
        )
    }

    override fun toControlWorkFromEntity(controlWorkEntity: ControlWorkEntity?): ControlWork {
        return ControlWork(
            controlWorkEntity?.field_index ?: "",
            controlWorkEntity?.date ?: "",
            controlWorkEntity?.field_group ?: "",
            controlWorkEntity?.theme ?: "",
            controlWorkEntity?.description ?: "",
            controlWorkEntity?.dateAddition ?: ""
        )
    }

    override fun toTermFromEntity(termEntity: TermEntity?): Term {
        return Term(
            termEntity?.subject ?: "",
            fromStringListJson(termEntity?.abbreviationMarks ?: ""),
            fromStringListJson(termEntity?.abbreviationMissedLessons ?: ""),
            fromStringListJson(termEntity?.average ?: ""),
            fromStringListJson(termEntity?.derived ?: ""),
            fromStringListJson(termEntity?.derivedInfoUrl ?: ""),
            fromStringListJson(termEntity?.credit ?: ""),
            fromStringListJson(termEntity?.creditInfoUrl ?: ""),
            fromStringListJson(termEntity?.additionalWorks ?: ""),
            fromStringListJson(termEntity?.exams ?: ""),
            termEntity?.yearDescription ?: "",
            termEntity?.yearMark ?: "",
            termEntity?.yearAdditionalWorks ?: "",
            termEntity?.yearExams ?: "",
            fromTermRangeJson(termEntity?.termRange ?: "")
        )
    }

    override fun toMessageGottenFromEntity(messageGottenEntity: MessageGottenEntity?): Message {
        return Message(
            messageGottenEntity?.messageId ?: "",
            messageGottenEntity?.isStarred.toBoolean(),
            messageGottenEntity?.wasSeen.toBoolean(),
            messageGottenEntity?.date ?: "",
            messageGottenEntity?.theme ?: "",
            messageGottenEntity?.sender ?: ""
        )
    }

    override fun toMessageSentFromEntity(messageSentEntity: MessageSentEntity?): Message {
        return Message(
            messageSentEntity?.messageId ?: "",
            messageSentEntity?.isStarred.toBoolean(),
            messageSentEntity?.wasSeen.toBoolean(),
            messageSentEntity?.date ?: "",
            messageSentEntity?.theme ?: "",
            messageSentEntity?.sender ?: ""
        )
    }

    override fun toMessageStarredFromEntity(messageStartedEntity: MessageStartedEntity?): Message {
        return Message(
            messageStartedEntity?.messageId ?: "",
            messageStartedEntity?.isStarred.toBoolean(),
            messageStartedEntity?.wasSeen.toBoolean(),
            messageStartedEntity?.date ?: "",
            messageStartedEntity?.theme ?: "",
            messageStartedEntity?.sender ?: ""
        )
    }

    override fun toMessageDeletedFromEntity(messageDeletedEntity: MessageDeletedEntity?): Message {
        return Message(
            messageDeletedEntity?.messageId ?: "",
            messageDeletedEntity?.isStarred.toBoolean(),
            messageDeletedEntity?.wasSeen.toBoolean(),
            messageDeletedEntity?.date ?: "",
            messageDeletedEntity?.theme ?: "",
            messageDeletedEntity?.sender ?: ""
        )
    }

    override fun toMessageIndividualFromEntity(messageIndividualEntity: MessageIndividualEntity?): MessageIndividual {
        return MessageIndividual(
            messageIndividualEntity?.messageId ?: "",
            messageIndividualEntity?.title ?: "",
            messageIndividualEntity?.sender ?: "",
            messageIndividualEntity?.date ?: "",
            messageIndividualEntity?.content ?: "",
            messageIndividualEntity?.recipients ?: "",
            fromMessageFilesJson(messageIndividualEntity?.files ?: "")
        )
    }

    override fun toHolidayFromEntity(holidayEntity: HolidayEntity?): Holiday {
        return  Holiday(
            holidayEntity?.name ?: "",
            holidayEntity?.rangeStart ?: "",
            holidayEntity?.rangeEnd ?: ""
        )
    }

    override fun toParentMeetingFromEntity(parentMeetingEntity: ParentMeetingEntity?): ParentMeeting {
        return ParentMeeting(
            parentMeetingEntity?.className ?: "",
            parentMeetingEntity?.description ?: "",
            parentMeetingEntity?.date ?: "",
            parentMeetingEntity?.location ?: "",
            fromParentMeetingFilesJson(parentMeetingEntity?.attachmentUrl ?: ""),
            parentMeetingEntity?.creationDate ?: ""
        )
    }

    override fun toScheduleFromEntity(scheduleEntity: ScheduleEntity?): Schedule {
        return Schedule(
            scheduleEntity?.weekDay!!.toLong(),
            scheduleEntity.timeRange ?: "",
            scheduleEntity.lessonOrder!!.toLong(),
            scheduleEntity.lesson ?: ""
        )
    }

    override fun toCalendarFromEntity(calendarEntity: CalendarEntity?): Calendar {
        return Calendar(
            calendarEntity?.title ?: "",
            calendarEntity?.start ?: "",
            calendarEntity?.url ?: "",
            calendarEntity?.type ?: "",
            calendarEntity?.allDay!!.toBoolean(),
        )
    }

    override fun toCalendarEventFromEntity(calendarEventEntity: CalendarEventEntity?): CalendarEvent {
        return CalendarEvent(
            calendarEventEntity?.url ?: "",
            calendarEventEntity?.teacher ?: "",
            calendarEventEntity?.theme ?: ""
        )
    }

    private fun fromSchoolNamesJson(json: String): List<SchoolInfo> {
        return jsonParser.fromJson<ArrayList<SchoolInfo>>(
            json,
            object : TypeToken<ArrayList<SchoolInfo>>(){}.type
        ) ?: emptyList()
    }

    private fun fromTermRangeJson(json: String): List<TermRange> {
        return jsonParser.fromJson<ArrayList<TermRange>>(
            json,
            object : TypeToken<ArrayList<TermRange>>(){}.type
        ) ?: emptyList()
    }

    private fun fromParentMeetingFilesJson(json: String): List<ParentMeetingFile> {
        return jsonParser.fromJson<ArrayList<ParentMeetingFile>>(
            json,
            object : TypeToken<ArrayList<ParentMeetingFile>>(){}.type
        ) ?: emptyList()
    }

    private fun fromMessageFilesJson(json: String): List<MessageFile> {
        return jsonParser.fromJson<ArrayList<MessageFile>>(
            json,
            object : TypeToken<ArrayList<MessageFile>>(){}.type
        ) ?: emptyList()
    }

    private fun fromStringListJson(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: emptyList()
    }

    private fun fromAttendanceJson(json: String): List<Int> {
        return jsonParser.fromJson<ArrayList<Int>>(
            json,
            object : TypeToken<ArrayList<Int>>(){}.type
        ) ?: emptyList()
    }

    private fun fromAttendanceRangeJson(json: String): List<AttendanceRange> {
        return jsonParser.fromJson<ArrayList<AttendanceRange>>(
            json,
            object : TypeToken<ArrayList<AttendanceRange>>(){}.type
        ) ?: emptyList()
    }

    private fun fromMarkEventJson(json: String): List<MarkEvent> {
        return jsonParser.fromJson<ArrayList<MarkEvent>>(
            json,
            object : TypeToken<ArrayList<MarkEvent>>(){}.type
        ) ?: emptyList()
    }

    private fun fromSchoolInfoJson(json: String): SchoolInfo? {
        return jsonParser.fromJson<SchoolInfo>(
            json,
            object : TypeToken<SchoolInfo>(){}.type
        )
    }
}