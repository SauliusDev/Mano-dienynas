package com.sunnyoaklabs.manodienynas.data.local

import com.sunnyoaklabs.manodienynas.domain.model.*
import kotlinx.coroutines.flow.Flow
import manodienynas.db.*

interface DataSource {

    suspend fun getSessionId(): SessionIdEntity?
    suspend fun deleteSessionId()
    suspend fun insertSessionId(sessionId: String)

    suspend fun getCredentials(): CredentialsEntity?
    suspend fun deleteCredentials()
    suspend fun insertEvent(credentials: Credentials)

    suspend fun getEventById(id: Long): EventEntity?
    fun getAllEvents(): Flow<List<EventEntity>>
    suspend fun deleteEventById(id: Long)
    suspend fun insertEvent(event: Event)

    suspend fun getMarkById(id: Long): MarkEntity?
    fun getAllMarks(): Flow<List<MarkEntity>>
    suspend fun deleteMarkById(id: Long)
    suspend fun insertMark(mark: Mark)

    suspend fun getAttendanceById(id: Long): AttendanceEntity?
    fun getAllAttendances(): Flow<List<AttendanceEntity>>
    suspend fun deleteAttendanceById(id: Long)
    suspend fun insertAttendance(attendance: Attendance)

    suspend fun getClassWorkById(id: Long): ClassworkEntity?
    fun getAllClassWorks(): Flow<List<ClassworkEntity>>
    suspend fun deleteClassWorkById(id: Long)
    suspend fun insertClassWork(classWork: ClassWork)

    suspend fun getHomeWorkById(id: Long): HomeworkEntity?
    fun getAllHomeWorks(): Flow<List<HomeworkEntity>>
    suspend fun deleteHomeWorkById(id: Long)
    suspend fun insertHomeWork(homeWork: HomeWork)

    suspend fun getControlWorkById(id: Long): ControlWorkEntity?
    fun getAllControlWorks(): Flow<List<ControlWorkEntity>>
    suspend fun deleteControlWorkById(id: Long)
    suspend fun insertControlWork(controlWork: ControlWork)

    suspend fun getTermById(id: Long): TermEntity?
    fun getAllTerms(): Flow<List<TermEntity>>
    suspend fun deleteTermById(id: Long)
    suspend fun insertTerm(term: Term)

    suspend fun getTermLegendById(id: Long): TermLegendEntity?
    fun getAllTermLegend(): Flow<List<TermLegendEntity>>
    suspend fun deleteTermLegendById(id: Long)
    suspend fun insertTermLegend(termLegend: TermLegend)

    suspend fun getMessageGottenById(id: Long): MessageGottenEntity?
    fun getAllMessagesGotten(): Flow<List<MessageGottenEntity>>
    suspend fun deleteMessageGottenById(id: Long)
    suspend fun insertMessageGotten(message: Message)

    suspend fun getMessageSentById(id: Long): MessageSentEntity?
    fun getAllMessagesSent(): Flow<List<MessageSentEntity>>
    suspend fun deleteMessageSentById(id: Long)
    suspend fun insertMessageSent(message: Message)

    suspend fun getMessageStarredById(id: Long): MessageStartedEntity?
    fun getAllMessagesStarred(): Flow<List<MessageStartedEntity>>
    suspend fun deleteMessageStarredById(id: Long)
    suspend fun insertMessageStarred(message: Message)

    suspend fun getMessageDeletedById(id: Long): MessageDeletedEntity?
    fun getAllMessagesDeleted(): Flow<List<MessageDeletedEntity>>
    suspend fun deleteMessageDeletedById(id: Long)
    suspend fun insertMessageDeleted(message: Message)

    suspend fun getMessageIndividualById(id: Long): MessageIndividualEntity?
    fun getAllMessagesIndividual(): Flow<List<MessageIndividualEntity>>
    suspend fun deleteMessageIndividualById(id: Long)
    suspend fun insertMessageIndividual(messageIndividual: MessageIndividual)

    suspend fun getHolidayById(id: Long): HolidayEntity?
    fun getAllHolidays(): Flow<List<HolidayEntity>>
    suspend fun deleteHolidayById(id: Long)
    suspend fun insertHoliday(holiday: Holiday)

    suspend fun getParentMeetingById(id: Long): ParentMeetingEntity?
    fun getAllParentMeetings(): Flow<List<ParentMeetingEntity>>
    suspend fun deleteParentMeetingById(id: Long)
    suspend fun insertParentMeeting(parentMeeting: ParentMeeting)

    suspend fun getScheduleById(id: Long): ScheduleEntity?
    fun getAllSchedule(): Flow<List<ScheduleEntity>>
    suspend fun deleteScheduleById(id: Long)
    suspend fun insertSchedule(schedule: Schedule)

    suspend fun getCalendarById(id: Long): CalendarEntity?
    fun getAllCalendars(): Flow<List<CalendarEntity>>
    suspend fun deleteCalendarById(id: Long)
    suspend fun insertCalendar(calendar: Calendar)
}