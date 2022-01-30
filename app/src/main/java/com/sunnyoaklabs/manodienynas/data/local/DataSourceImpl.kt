package com.sunnyoaklabs.manodienynas.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.sunnyoaklabs.manodienynas.ManoDienynasDatabase
import com.sunnyoaklabs.manodienynas.core.util.DispatcherProvider
import com.sunnyoaklabs.manodienynas.core.util.toLong
import com.sunnyoaklabs.manodienynas.data.util.Converter
import com.sunnyoaklabs.manodienynas.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import manodienynas.db.*
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val db: ManoDienynasDatabase,
    private val dispatchers: DispatcherProvider,
    private val converter: Converter
): DataSource {

    override suspend fun getUserSetting(): UserSettingEntity? {
        return withContext(Dispatchers.IO) {
            db.userSettingsEntityQueries.getUserSetting().executeAsOneOrNull()
        }
    }

    override suspend fun deleteUserSetting() {
        return withContext(dispatchers.io) {
            db.userSettingsEntityQueries.deleteUserSetting()
        }
    }

    override suspend fun insertUserSetting(userSettings: UserSettings) {
        return withContext(dispatchers.io) {
            db.userSettingsEntityQueries.insertUserSetting(userSettings.keepSignedIn.toLong())
        }
    }

    override suspend fun getSessionId(): SessionIdEntity? {
        return withContext(dispatchers.io) {
            db.sessionIdEntityQueries.getSessionId().executeAsOneOrNull()
        }
    }

    override suspend fun deleteSessionId() {
        withContext(dispatchers.io) {
            db.sessionIdEntityQueries.deleteSessionId()
        }
    }

    override suspend fun insertSessionId(sessionId: String) {
        return withContext(dispatchers.io) {
            db.sessionIdEntityQueries.insertSessionId(
                sessionId
            )
        }
    }

    override suspend fun getCredentials(): CredentialsEntity? {
        return withContext(dispatchers.io) {
            db.credentialsEntityQueries.getCredentials().executeAsOneOrNull()
        }
    }

    override suspend fun deleteCredentials() {
        withContext(dispatchers.io) {
            db.credentialsEntityQueries.deleteCredentials()
        }
    }

    override suspend fun insertEvent(credentials: Credentials) {
        return withContext(dispatchers.io) {
            db.credentialsEntityQueries.insertCredentials(
                credentials.username,
                credentials.password
            )
        }
    }

    override suspend fun getEventById(id: Long): EventEntity? {
        return withContext(dispatchers.io) {
            db.eventsEntityQueries.getEventById(id).executeAsOneOrNull()
        }
    }

    override fun getAllEvents(): Flow<List<EventEntity>> {
        return db.eventsEntityQueries.getAllEvents().asFlow().mapToList()
    }

    override suspend fun deleteEventById(id: Long) {
        withContext(dispatchers.io) {
            db.eventsEntityQueries.deleteEventById(id)
        }
    }

    override suspend fun insertEvent(event: Event) {
        return withContext(dispatchers.io) {
            db.eventsEntityQueries.insertEvent(
                event.id,
                event.title,
                event.pupilInfo,
                event.createDate,
                event.createDateText,
                event.eventHeader,
                event.eventText,
                event.creator_name
            )
        }
    }

    override suspend fun getMarkById(id: Long): MarkEntity? {
        return withContext(dispatchers.io) {
            db.marksEntityQueries.getMarkById(id).executeAsOneOrNull()
        }
    }

    override fun getAllMarks(): Flow<List<MarkEntity>> {
        return db.marksEntityQueries.getAllMarks().asFlow().mapToList()
    }

    override suspend fun deleteMarkById(id: Long) {
        withContext(dispatchers.io) {
            db.marksEntityQueries.deleteMarkById(id)
        }
    }

    override suspend fun insertMark(mark: Mark) {
        return withContext(dispatchers.io) {
            db.marksEntityQueries.insertMark(
                mark.id,
                mark.lesson,
                mark.teacher,
                mark.average,
                converter.toMarkEventJson(mark.markEvent)
            )
        }
    }

    override suspend fun getAttendanceById(id: Long): AttendanceEntity? {
        return withContext(dispatchers.io) {
            db.attendanceEntityQueries.getAttendanceById(id).executeAsOneOrNull()
        }
    }

    override fun getAllAttendances(): Flow<List<AttendanceEntity>> {
        return db.attendanceEntityQueries.getAllAttendance().asFlow().mapToList()
    }

    override suspend fun deleteAttendanceById(id: Long) {
        withContext(dispatchers.io) {
            db.attendanceEntityQueries.deleteAttendanceById(id)
        }
    }

    override suspend fun insertAttendance(attendance: Attendance) {
        return withContext(dispatchers.io) {
            db.marksEntityQueries.insertMark(
                attendance.id,
                attendance.lessonTitle,
                attendance.teacher,
                converter.toAttendanceJson(attendance.attendance),
                converter.toAttendanceRangeJson(attendance.attendanceRange)
            )
        }
    }

    override suspend fun getClassWorkById(id: Long): ClassworkEntity? {
        return withContext(dispatchers.io) {
            db.classworkEntityQueries.getClassworkById(id).executeAsOneOrNull()
        }
    }

    override fun getAllClassWorks(): Flow<List<ClassworkEntity>> {
        return db.classworkEntityQueries.getAllClasswork().asFlow().mapToList()
    }

    override suspend fun deleteClassWorkById(id: Long) {
        withContext(dispatchers.io) {
            db.classworkEntityQueries.deleteClassworkById(id)
        }
    }

    override suspend fun insertClassWork(classWork: ClassWork) {
        return withContext(dispatchers.io) {
            db.classworkEntityQueries.insertClasswork(
                classWork.id,
                classWork.month,
                classWork.monthDay,
                classWork.weekDay,
                classWork.lesson,
                classWork.teacher,
                classWork.description,
                classWork.dateAddition
            )
        }
    }

    override suspend fun getHomeWorkById(id: Long): HomeworkEntity? {
        return withContext(dispatchers.io) {
            db.homeworkEntityQueries.getHomeworkById(id).executeAsOneOrNull()
        }
    }

    override fun getAllHomeWorks(): Flow<List<HomeworkEntity>> {
        return db.homeworkEntityQueries.getAllHomework().asFlow().mapToList()
    }

    override suspend fun deleteHomeWorkById(id: Long) {
        withContext(dispatchers.io) {
            db.homeworkEntityQueries.deleteHomeworkById(id)
        }
    }

    override suspend fun insertHomeWork(homeWork: HomeWork) {
        return withContext(dispatchers.io) {
            db.homeworkEntityQueries.insertHomework(
                homeWork.id,
                homeWork.month,
                homeWork.monthDay,
                homeWork.weekDay,
                homeWork.lesson,
                homeWork.teacher,
                homeWork.description,
                homeWork.dateAddition,
                homeWork.attachmentsUrl
            )
        }
    }

    override suspend fun getControlWorkById(id: Long): ControlWorkEntity? {
        return withContext(dispatchers.io) {
            db.controlWorkEntityQueries.getControlWorkById(id).executeAsOneOrNull()
        }
    }

    override fun getAllControlWorks(): Flow<List<ControlWorkEntity>> {
        return db.controlWorkEntityQueries.getAllControlWork().asFlow().mapToList()
    }

    override suspend fun deleteControlWorkById(id: Long) {
        withContext(dispatchers.io) {
            db.controlWorkEntityQueries.deleteControlWorkById(id)
        }
    }

    override suspend fun insertControlWork(controlWork: ControlWork) {
        return withContext(dispatchers.io) {
            db.controlWorkEntityQueries.insertControlWork(
                controlWork.id,
                controlWork.index,
                controlWork.date,
                controlWork.group,
                controlWork.theme,
                controlWork.dateAddition
            )
        }
    }

    override suspend fun getTermById(id: Long): TermEntity? {
        return withContext(dispatchers.io) {
            db.termEntityQueries.getTermById(id).executeAsOneOrNull()
        }
    }

    override fun getAllTerms(): Flow<List<TermEntity>> {
        return db.termEntityQueries.getAllTerm().asFlow().mapToList()
    }

    override suspend fun deleteTermById(id: Long) {
        withContext(dispatchers.io) {
            db.termEntityQueries.deleteTermById(id)
        }
    }

    override suspend fun insertTerm(term: Term) {
        return withContext(dispatchers.io) {
            db.termEntityQueries.insertTerm(
                term.id,
                term.subject,
                term.abbreviationMarks,
                term.abbreviationMissedLessons,
                term.average,
                term.derived,
                term.credit,
                term.additionalWorks,
                term.exams,
                term.yearDescription,
                term.yearMark,
                term.yearAdditionalWorks,
                term.yearExams
            )
        }
    }

    override suspend fun getTermLegendById(id: Long): TermLegendEntity? {
        return withContext(dispatchers.io) {
            db.termLegendEntityQueries.getTermLegendById(id).executeAsOneOrNull()
        }
    }

    override fun getAllTermLegend(): Flow<List<TermLegendEntity>> {
        return db.termLegendEntityQueries.getAllTermLegend().asFlow().mapToList()
    }

    override suspend fun deleteTermLegendById(id: Long) {
        withContext(dispatchers.io) {
            db.termLegendEntityQueries.deleteTermLegendById(id)
        }
    }

    override suspend fun insertTermLegend(termLegend: TermLegend) {
        return withContext(dispatchers.io) {
            db.termLegendEntityQueries.insertTermLegend(
                termLegend.id,
                termLegend.abbreviation,
                termLegend.description
            )
        }
    }

    override suspend fun getMessageGottenById(id: Long): MessageGottenEntity? {
        return withContext(dispatchers.io) {
            db.messagesGottenEntityQueries.getMessageGottenById(id).executeAsOneOrNull()
        }
    }

    override fun getAllMessagesGotten(): Flow<List<MessageGottenEntity>> {
        return db.messagesGottenEntityQueries.getAllMessagesGotten().asFlow().mapToList()
    }

    override suspend fun deleteMessageGottenById(id: Long) {
        withContext(dispatchers.io) {
            db.messagesGottenEntityQueries.deleteMessageGottenById(id)
        }
    }

    override suspend fun insertMessageGotten(message: Message) {
        return withContext(dispatchers.io) {
            db.messagesGottenEntityQueries.insertMessageGotten(
                message.id,
                message.messageId,
                message.isStarred,
                message.date,
                message.theme,
                message.sender
            )
        }
    }

    override suspend fun getMessageSentById(id: Long): MessageSentEntity? {
        return withContext(dispatchers.io) {
            db.messagesSentEntityQueries.getMessageSentById(id).executeAsOneOrNull()
        }
    }

    override fun getAllMessagesSent(): Flow<List<MessageSentEntity>> {
        return db.messagesSentEntityQueries.getAllMessagesSent().asFlow().mapToList()
    }

    override suspend fun deleteMessageSentById(id: Long) {
        withContext(dispatchers.io) {
            db.messagesSentEntityQueries.deleteMessageSentById(id)
        }
    }

    override suspend fun insertMessageSent(message: Message) {
        return withContext(dispatchers.io) {
            db.messagesSentEntityQueries.insertMessageSent(
                message.id,
                message.messageId,
                message.isStarred,
                message.date,
                message.theme,
                message.sender
            )
        }
    }

    override suspend fun getMessageStarredById(id: Long): MessageStartedEntity? {
        return withContext(dispatchers.io) {
            db.messagesStarredEntityQueries.getMessageStartedById(id).executeAsOneOrNull()
        }
    }

    override fun getAllMessagesStarred(): Flow<List<MessageStartedEntity>> {
        return db.messagesStarredEntityQueries.getAllMessagesStarted().asFlow().mapToList()
    }

    override suspend fun deleteMessageStarredById(id: Long) {
        withContext(dispatchers.io) {
            db.messagesStarredEntityQueries.deleteMessageStartedById(id)
        }
    }

    override suspend fun insertMessageStarred(message: Message) {
        return withContext(dispatchers.io) {
            db.messagesStarredEntityQueries.insertMessageStarted(
                message.id,
                message.messageId,
                message.isStarred,
                message.date,
                message.theme,
                message.sender
            )
        }
    }

    override suspend fun getMessageDeletedById(id: Long): MessageDeletedEntity? {
        return withContext(dispatchers.io) {
            db.messagesDeletedEntityQueries.getMessageDeletedById(id).executeAsOneOrNull()
        }
    }

    override fun getAllMessagesDeleted(): Flow<List<MessageDeletedEntity>> {
        return db.messagesDeletedEntityQueries.getAllMessagesDeleted().asFlow().mapToList()
    }

    override suspend fun deleteMessageDeletedById(id: Long) {
        withContext(dispatchers.io) {
            db.messagesDeletedEntityQueries.deleteMessageDeletedById(id)
        }
    }

    override suspend fun insertMessageDeleted(message: Message) {
        return withContext(dispatchers.io) {
            db.messagesDeletedEntityQueries.insertMessageDeleted(
                message.id,
                message.messageId,
                message.isStarred,
                message.date,
                message.theme,
                message.sender
            )
        }
    }

    override suspend fun getMessageIndividualById(id: Long): MessageIndividualEntity? {
        return withContext(dispatchers.io) {
            db.messagesIndividualEntityQueries.getMessageIndividualById(id).executeAsOneOrNull()
        }
    }

    override fun getAllMessagesIndividual(): Flow<List<MessageIndividualEntity>> {
        return db.messagesIndividualEntityQueries.getAllMessagesIndividual().asFlow().mapToList()
    }

    override suspend fun deleteMessageIndividualById(id: Long) {
        withContext(dispatchers.io) {
            db.messagesIndividualEntityQueries.deleteMessageIndividualById(id)
        }
    }

    override suspend fun insertMessageIndividual(messageIndividual: MessageIndividual) {
        return withContext(dispatchers.io) {
            db.messagesIndividualEntityQueries.insertMessageIndividual(
                messageIndividual.id,
                messageIndividual.messageId,
                messageIndividual.title,
                messageIndividual.sender,
                messageIndividual.date,
                messageIndividual.content
            )
        }
    }

    override suspend fun getHolidayById(id: Long): HolidayEntity? {
        return withContext(dispatchers.io) {
            db.holidayEntityQueries.getHolidayById(id).executeAsOneOrNull()
        }
    }

    override fun getAllHolidays(): Flow<List<HolidayEntity>> {
        return db.holidayEntityQueries.getAllHoliday().asFlow().mapToList()
    }

    override suspend fun deleteHolidayById(id: Long) {
        withContext(dispatchers.io) {
            db.holidayEntityQueries.deleteHolidayById(id)
        }
    }

    override suspend fun insertHoliday(holiday: Holiday) {
        return withContext(dispatchers.io) {
            db.holidayEntityQueries.insertHoliday(
                holiday.id,
                holiday.name,
                holiday.rangeStart,
                holiday.rangeEnd
            )
        }
    }

    override suspend fun getParentMeetingById(id: Long): ParentMeetingEntity? {
        return withContext(dispatchers.io) {
            db.parentMeetingsEntityQueries.getParentMeetingById(id).executeAsOneOrNull()
        }
    }

    override fun getAllParentMeetings(): Flow<List<ParentMeetingEntity>> {
        return db.parentMeetingsEntityQueries.getAllParentMeetings().asFlow().mapToList()
    }

    override suspend fun deleteParentMeetingById(id: Long) {
        withContext(dispatchers.io) {
            db.parentMeetingsEntityQueries.deleteParentMeetingById(id)
        }
    }

    override suspend fun insertParentMeeting(parentMeeting: ParentMeeting) {
        return withContext(dispatchers.io) {
            db.parentMeetingsEntityQueries.insertParentMeeting(
                parentMeeting.id,
                parentMeeting.className,
                parentMeeting.description,
                parentMeeting.creationDate,
                parentMeeting.location,
                parentMeeting.attachmentUrl,
                parentMeeting.creationDate
            )
        }
    }

    override suspend fun getScheduleById(id: Long): ScheduleEntity? {
        return withContext(dispatchers.io) {
            db.scheduleEntityQueries.getScheduleById(id).executeAsOneOrNull()
        }
    }

    override fun getAllSchedule(): Flow<List<ScheduleEntity>> {
        return db.scheduleEntityQueries.getAllSchedule().asFlow().mapToList()
    }

    override suspend fun deleteScheduleById(id: Long) {
        withContext(dispatchers.io) {
            db.scheduleEntityQueries.deleteScheduleById(id)
        }
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        return withContext(dispatchers.io) {
            db.scheduleEntityQueries.insertSchedule(
                schedule.id,
                schedule.timeRange,
                schedule.lessonOrder,
                schedule.lesson
            )
        }
    }

    override suspend fun getCalendarById(id: Long): CalendarEntity? {
        return withContext(dispatchers.io) {
            db.calendarEntityQueries.getCalendarById(id).executeAsOneOrNull()
        }
    }

    override fun getAllCalendars(): Flow<List<CalendarEntity>> {
        return db.calendarEntityQueries.getAllCalendar().asFlow().mapToList()
    }

    override suspend fun deleteCalendarById(id: Long) {
        withContext(dispatchers.io) {
            db.calendarEntityQueries.deleteCalendarById(id)
        }
    }

    override suspend fun insertCalendar(calendar: Calendar) {
        return withContext(dispatchers.io) {
            db.calendarEntityQueries.insertCalendar(
                calendar.id,
                calendar.allDay.toLong(),
                calendar.type,
                calendar.start,
                calendar.title
            )
        }
    }
}