package com.sunnyoaklabs.manodienynas.data.local

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.sunnyoaklabs.manodienynas.ManoDienynasDatabase
import com.sunnyoaklabs.manodienynas.core.util.DispatcherProvider
import com.sunnyoaklabs.manodienynas.core.util.toLong
import com.sunnyoaklabs.manodienynas.data.util.Converter
import com.sunnyoaklabs.manodienynas.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import manodienynas.db.*
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val db: ManoDienynasDatabase,
    private val dispatchers: DispatcherProvider,
    private val converter: Converter
) : DataSource {

    override suspend fun getUser(): UserEntity? {
        return withContext(dispatchers.io) {
            db.userEntityQueries.getUser().executeAsOneOrNull()
        }
    }

    override suspend fun deleteUser() {
        return withContext(dispatchers.io) {
            db.userEntityQueries.deleteUser()
        }
    }

    override suspend fun insertUser(user: User) {
        return withContext(dispatchers.io) {
            db.userEntityQueries.insertUser(
                user.name,
                user.role,
                converter.toSchoolNamesJson(user.schoolsNames)
            )
        }
    }

    override suspend fun getSettings(): SettingsEntity? {
        return withContext(dispatchers.io) {
            db.settingsEntityQueries.getSettings().executeAsOneOrNull()
        }
    }

    override suspend fun deleteSettings() {
        return withContext(dispatchers.io) {
            db.settingsEntityQueries.deleteSettings()
        }
    }

    override suspend fun insertSettings(settings: Settings) {
        return withContext(dispatchers.io) {
            db.settingsEntityQueries.insertSettings(settings.keepSignedIn.toLong())
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

    override suspend fun insertCredentials(credentials: Credentials) {
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

    override fun getAllEvents(): List<EventEntity> {
        return db.eventsEntityQueries.getAllEvents().executeAsList()
    }

    override suspend fun deleteAllEvents() {
        withContext(dispatchers.io) {
            db.eventsEntityQueries.deleteAllEvents()
        }
    }

    override suspend fun insertEvent(event: Event) {
        return withContext(dispatchers.io) {
            db.eventsEntityQueries.insertEvent(
                null,
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

    override fun getAllMarks(): List<MarkEntity> {
        return db.marksEntityQueries.getAllMarks().executeAsList()
    }

    override suspend fun deleteAllMarks() {
        withContext(dispatchers.io) {
            db.marksEntityQueries.deleteAllMarks()
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

    override fun getAllAttendances(): List<AttendanceEntity> {
        return db.attendanceEntityQueries.getAllAttendance().executeAsList()
    }

    override suspend fun deleteAllAttendance() {
        withContext(dispatchers.io) {
            db.attendanceEntityQueries.deleteAllAttendance()
        }
    }

    override suspend fun insertAttendance(attendance: Attendance) {
        return withContext(dispatchers.io) {
            db.attendanceEntityQueries.insertAttendance(
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

    override fun getAllClassWorks(): List<ClassworkEntity> {
        return db.classworkEntityQueries.getAllClasswork().executeAsList()
    }

    override suspend fun deleteAllClassWork() {
        withContext(dispatchers.io) {
            db.classworkEntityQueries.deleteAllClasswork()
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
                classWork.dateAddition,
                classWork.attachmentsUrl
            )
        }
    }

    override suspend fun getHomeWorkById(id: Long): HomeworkEntity? {
        return withContext(dispatchers.io) {
            db.homeworkEntityQueries.getHomeworkById(id).executeAsOneOrNull()
        }
    }

    override fun getAllHomeWorks(): List<HomeworkEntity> {
        return db.homeworkEntityQueries.getAllHomework().executeAsList()
    }

    override suspend fun deleteAllHomeWork() {
        withContext(dispatchers.io) {
            db.homeworkEntityQueries.deleteAllHomework()
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

    override fun getAllControlWorks(): List<ControlWorkEntity> {
        return db.controlWorkEntityQueries.getAllControlWork().executeAsList()
    }

    override suspend fun deleteAllControlWork() {
        withContext(dispatchers.io) {
            db.controlWorkEntityQueries.deleteAllControlWork()
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
                controlWork.description,
                controlWork.dateAddition
            )
        }
    }

    override suspend fun getTermById(id: Long): TermEntity? {
        return withContext(dispatchers.io) {
            db.termEntityQueries.getTermById(id).executeAsOneOrNull()
        }
    }

    override fun getAllTerms(): List<TermEntity> {
        return db.termEntityQueries.getAllTerm().executeAsList()
    }

    override suspend fun deleteAllTerm() {
        withContext(dispatchers.io) {
            db.termEntityQueries.deleteAllTerm()
        }
    }

    override suspend fun insertTerm(term: Term) {
        return withContext(dispatchers.io) {
            db.termEntityQueries.insertTerm(
                term.id,
                term.subject,
                converter.toStringListJson(term.abbreviationMarks),
                converter.toStringListJson(term.abbreviationMissedLessons),
                converter.toStringListJson(term.average),
                converter.toStringListJson(term.derived),
                converter.toStringListJson(term.derivedInfoUrl),
                converter.toStringListJson(term.credit),
                converter.toStringListJson(term.creditInfoUrl),
                converter.toStringListJson(term.additionalWorks),
                converter.toStringListJson(term.exams),
                term.yearDescription,
                term.yearMark,
                term.yearAdditionalWorks,
                term.yearExams,
                converter.toTermRangeJson(term.termRange)
            )
        }
    }

    override suspend fun getMessageGottenById(id: Long): MessageGottenEntity? {
        return withContext(dispatchers.io) {
            db.messagesGottenEntityQueries.getMessageGottenById(id).executeAsOneOrNull()
        }
    }

    override fun getAllMessagesGotten(): List<MessageGottenEntity> {
        return db.messagesGottenEntityQueries.getAllMessagesGotten().executeAsList()
    }

    override suspend fun deleteAllMessageGotten() {
        withContext(dispatchers.io) {
            db.messagesGottenEntityQueries.deleteAllMessagesGotten()
        }
    }

    override suspend fun insertMessageGotten(message: Message) {
        return withContext(dispatchers.io) {
            db.messagesGottenEntityQueries.insertMessageGotten(
                message.id,
                message.messageId,
                message.isStarred.toString(),
                message.wasSeen.toString(),
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

    override fun getAllMessagesSent(): List<MessageSentEntity> {
        return db.messagesSentEntityQueries.getAllMessagesSent().executeAsList()
    }

    override suspend fun deleteAllMessageSent() {
        withContext(dispatchers.io) {
            db.messagesSentEntityQueries.deleteAllMessagesSent()
        }
    }

    override suspend fun insertMessageSent(message: Message) {
        return withContext(dispatchers.io) {
            db.messagesSentEntityQueries.insertMessageSent(
                message.id,
                message.messageId,
                message.isStarred.toString(),
                message.wasSeen.toString(),
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

    override fun getAllMessagesStarred(): List<MessageStartedEntity> {
        return db.messagesStarredEntityQueries.getAllMessagesStarted().executeAsList()
    }

    override suspend fun deleteAllMessageStarred() {
        withContext(dispatchers.io) {
            db.messagesStarredEntityQueries.deleteAllMessagesStarted()
        }
    }

    override suspend fun insertMessageStarred(message: Message) {
        return withContext(dispatchers.io) {
            db.messagesStarredEntityQueries.insertMessageStarted(
                message.id,
                message.messageId,
                message.isStarred.toString(),
                message.wasSeen.toString(),
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

    override fun getAllMessagesDeleted(): List<MessageDeletedEntity> {
        return db.messagesDeletedEntityQueries.getAllMessagesDeleted().executeAsList()
    }

    override suspend fun deleteAllMessageDeleted() {
        withContext(dispatchers.io) {
            db.messagesDeletedEntityQueries.deleteAllMessagesDeleted()
        }
    }

    override suspend fun insertMessageDeleted(message: Message) {
        return withContext(dispatchers.io) {
            db.messagesDeletedEntityQueries.insertMessageDeleted(
                message.id,
                message.messageId,
                message.isStarred.toString(),
                message.wasSeen.toString(),
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

    override fun getAllMessagesIndividual(): List<MessageIndividualEntity> {
        return db.messagesIndividualEntityQueries.getAllMessagesIndividual().executeAsList()
    }

    override suspend fun deleteAllMessageIndividual() {
        withContext(dispatchers.io) {
            db.messagesIndividualEntityQueries.deleteAllMessagesIndividual()
        }
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
                messageIndividual.content,
                messageIndividual.recipients,
                converter.toMessageFilesJson(messageIndividual.files)
            )
        }
    }

    override suspend fun getHolidayById(id: Long): HolidayEntity? {
        return withContext(dispatchers.io) {
            db.holidayEntityQueries.getHolidayById(id).executeAsOneOrNull()
        }
    }

    override fun getAllHolidays(): List<HolidayEntity> {
        return db.holidayEntityQueries.getAllHoliday().executeAsList()
    }

    override suspend fun deleteAllHoliday() {
        withContext(dispatchers.io) {
            db.holidayEntityQueries.deleteAllHoliday()
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

    override fun getAllParentMeetings(): List<ParentMeetingEntity> {
        return db.parentMeetingsEntityQueries.getAllParentMeetings().executeAsList()
    }

    override suspend fun deleteAllParentMeeting() {
        withContext(dispatchers.io) {
            db.parentMeetingsEntityQueries.deleteAllParentMeetings()
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
                converter.toParentMeetingFilesJson(parentMeeting.attachmentUrls),
                parentMeeting.creationDate
            )
        }
    }

    override suspend fun getScheduleById(id: Long): ScheduleEntity? {
        return withContext(dispatchers.io) {
            db.scheduleEntityQueries.getScheduleById(id).executeAsOneOrNull()
        }
    }

    override fun getAllSchedule(): List<ScheduleEntity> {
        return db.scheduleEntityQueries.getAllSchedule().executeAsList()
    }

    override suspend fun deleteAllSchedule() {
        withContext(dispatchers.io) {
            db.scheduleEntityQueries.deleteAllSchedule()
        }
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        return withContext(dispatchers.io) {
            db.scheduleEntityQueries.insertSchedule(
                schedule.id,
                schedule.weekDay,
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

    override fun getAllCalendars(): List<CalendarEntity> {
        return db.calendarEntityQueries.getAllCalendar().executeAsList()
    }

    override suspend fun deleteAllCalendar() {
        withContext(dispatchers.io) {
            db.calendarEntityQueries.deleteAllCalendar()
        }
    }

    override suspend fun insertCalendar(calendar: Calendar) {
        return withContext(dispatchers.io) {
            db.calendarEntityQueries.insertCalendar(
                calendar.id,
                calendar.title,
                calendar.start,
                calendar.url,
                calendar.type,
                calendar.allDay.toLong()
            )
        }
    }
}