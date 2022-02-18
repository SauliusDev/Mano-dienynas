package com.sunnyoaklabs.manodienynas.data.local

import com.sunnyoaklabs.manodienynas.domain.model.*
import manodienynas.db.*

interface DataSource {

    suspend fun getState(): StateEntity?
    suspend fun deleteState()
    suspend fun insertState(state: State)

    suspend fun getUser(): UserEntity?
    suspend fun deleteUser()
    suspend fun insertUser(user: User)

    suspend fun getSettings(): SettingsEntity?
    suspend fun deleteSettings()
    suspend fun insertSettings(settings: Settings)

    suspend fun getCredentials(): CredentialsEntity?
    suspend fun deleteCredentials()
    suspend fun insertCredentials(credentials: Credentials)

    suspend fun getEventById(id: Long): EventEntity?
    fun getAllEvents(): List<EventEntity>
    suspend fun deleteAllEvents()
    suspend fun insertEvent(event: Event)

    suspend fun getMarkById(id: Long): MarkEntity?
    fun getAllMarks(): List<MarkEntity>
    suspend fun deleteAllMarks()
    suspend fun insertMark(mark: Mark)

    suspend fun getAttendanceById(id: Long): AttendanceEntity?
    fun getAllAttendances(): List<AttendanceEntity>
    suspend fun deleteAllAttendance()
    suspend fun insertAttendance(attendance: Attendance)

    suspend fun getClassWorkById(id: Long): ClassworkEntity?
    fun getAllClassWorks(): List<ClassworkEntity>
    suspend fun deleteAllClassWork()
    suspend fun insertClassWork(classWork: ClassWork)

    suspend fun getHomeWorkById(id: Long): HomeworkEntity?
    fun getAllHomeWorks(): List<HomeworkEntity>
    suspend fun deleteAllHomeWork()
    suspend fun insertHomeWork(homeWork: HomeWork)

    suspend fun getControlWorkById(id: Long): ControlWorkEntity?
    fun getAllControlWorks(): List<ControlWorkEntity>
    suspend fun deleteAllControlWork()
    suspend fun insertControlWork(controlWork: ControlWork)

    suspend fun getTermById(id: Long): TermEntity?
    fun getAllTerms(): List<TermEntity>
    suspend fun deleteAllTerm()
    suspend fun insertTerm(term: Term)

    suspend fun getMessageGottenById(id: Long): MessageGottenEntity?
    fun getAllMessagesGotten(): List<MessageGottenEntity>
    suspend fun deleteAllMessageGotten()
    suspend fun insertMessageGotten(message: Message)

    suspend fun getMessageSentById(id: Long): MessageSentEntity?
    fun getAllMessagesSent(): List<MessageSentEntity>
    suspend fun deleteAllMessageSent()
    suspend fun insertMessageSent(message: Message)

    suspend fun getMessageStarredById(id: Long): MessageStartedEntity?
    fun getAllMessagesStarred(): List<MessageStartedEntity>
    suspend fun deleteAllMessageStarred()
    suspend fun insertMessageStarred(message: Message)

    suspend fun getMessageDeletedById(id: Long): MessageDeletedEntity?
    fun getAllMessagesDeleted(): List<MessageDeletedEntity>
    suspend fun deleteAllMessageDeleted()
    suspend fun insertMessageDeleted(message: Message)

    suspend fun getMessageIndividualById(id: Long): MessageIndividualEntity?
    fun getAllMessagesIndividual(): List<MessageIndividualEntity>
    suspend fun deleteAllMessageIndividual()
    suspend fun deleteMessageIndividualById(id: Long)
    suspend fun insertMessageIndividual(messageIndividual: MessageIndividual)

    suspend fun getHolidayById(id: Long): HolidayEntity?
    fun getAllHolidays(): List<HolidayEntity>
    suspend fun deleteAllHoliday()
    suspend fun insertHoliday(holiday: Holiday)

    suspend fun getParentMeetingById(id: Long): ParentMeetingEntity?
    fun getAllParentMeetings(): List<ParentMeetingEntity>
    suspend fun deleteAllParentMeeting()
    suspend fun insertParentMeeting(parentMeeting: ParentMeeting)

    suspend fun getScheduleById(id: Long): ScheduleEntity?
    fun getAllSchedule(): List<ScheduleEntity>
    suspend fun deleteAllSchedule()
    suspend fun insertSchedule(schedule: Schedule)

    suspend fun getCalendarById(id: Long): CalendarEntity?
    fun getAllCalendars(): List<CalendarEntity>
    suspend fun deleteAllCalendar()
    suspend fun deleteCalendarById(id: Long)
    suspend fun insertCalendar(calendar: Calendar)

    suspend fun getCalendarEventByUrl(url: String): CalendarEventEntity?
    suspend fun deleteAllCalendarEvent()
    suspend fun deleteCalendarEventByUrl(url: String)
    suspend fun insertCalendarEvent(calendarEvent: CalendarEvent)
}