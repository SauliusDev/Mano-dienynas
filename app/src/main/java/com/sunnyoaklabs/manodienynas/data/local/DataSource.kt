package com.sunnyoaklabs.manodienynas.data.local

import com.sunnyoaklabs.manodienynas.domain.model.*
import kotlinx.coroutines.flow.Flow

interface DataSource {

    suspend fun getEventById(id: Long): Event?
    fun getAllEvents(): Flow<List<Event>>
    suspend fun deleteEventById(id: Long)
    suspend fun insertEvent(event: Event)

    suspend fun getMarksById(id: Long): Marks?
    fun getAllMarks(): Flow<List<Marks>>
    suspend fun deleteMarksById(id: Long)
    suspend fun insert(marks: Marks)

    suspend fun getAttendanceById(id: Long): Attendance?
    fun getAllAttendance(): Flow<List<Attendance>>
    suspend fun deleteAttendanceById(id: Long)
    suspend fun insertAttendance(attendance: Attendance)

    suspend fun getClassWorkById(id: Long): ClassWork?
    fun getAllClassWork(): Flow<List<ClassWork>>
    suspend fun deleteClassWorkById(id: Long)
    suspend fun insertClassWork(classWork: ClassWork)

    suspend fun getHomeWorkById(id: Long): HomeWork?
    fun getAllHomeWork(): Flow<List<HomeWork>>
    suspend fun deleteHomeWorkById(id: Long)
    suspend fun insertHomeWork(homeWork: HomeWork)

    suspend fun getControlWorkById(id: Long): ControlWork?
    fun getAllControlWork(): Flow<List<ControlWork>>
    suspend fun deleteControlWorkById(id: Long)
    suspend fun insert(controlWork: ControlWork)

    suspend fun getTermById(id: Long): Term?
    fun getAllTerm(): Flow<List<Term>>
    suspend fun deleteTermById(id: Long)
    suspend fun insert(term: Term)

    suspend fun getTermLegendById(id: Long): TermLegend?
    fun getAllTermLegend(): Flow<TermLegend>
    suspend fun deleteTermLegendById(id: Long)
    suspend fun insert(termLegend: TermLegend)

    suspend fun getMessagesGottenById(id: Long): Message?
    fun getMessagesGottenAll(): Flow<List<Message>>
    suspend fun deleteMessagesGottenById(id: Long)
    suspend fun insertMessagesGotten(message: Message)

    suspend fun getMessagesSentById(id: Long): Message?
    fun getAllMessagesSent(): Flow<List<Message>>
    suspend fun deleteMessagesSentById(id: Long)
    suspend fun insertMessagesSent(message: Message)

    suspend fun getMessagesStarredById(id: Long): Message?
    fun getAllMessagesStarred(): Flow<List<Message>>
    suspend fun deleteMessagesStarredById(id: Long)
    suspend fun insertMessagesStarred(message: Message)

    suspend fun getMessagesDeletedById(id: Long): Message?
    fun getAllMessagesDeleted(): Flow<List<Message>>
    suspend fun deleteMessagesDeletedById(id: Long)
    suspend fun insertMessagesDeleted(message: Message)

    suspend fun getMessageIndividualById(id: Long): MessageIndividual?
    fun getAllMessageIndividual(): Flow<List<MessageIndividual>>
    suspend fun deleteMessageIndividualById(id: Long)
    suspend fun insertMessageIndividual(messageIndividual: MessageIndividual)

    suspend fun getHolidayById(id: Long): Holiday?
    fun getAllHoliday(): Flow<List<Holiday>>
    suspend fun deleteHolidayById(id: Long)
    suspend fun insertHoliday(holiday: Holiday)

    suspend fun getParentMeetingsById(id: Long): ParentMeetings?
    fun getAllParentMeetings(): Flow<List<ParentMeetings>>
    suspend fun deleteParentMeetingsById(id: Long)
    suspend fun insertParentMeetings(parentMeetings: ParentMeetings)

    suspend fun getScheduleById(id: Long): Schedule?
    fun getAllSchedule(): Flow<List<Schedule>>
    suspend fun deleteScheduleById(id: Long)
    suspend fun insertSchedule(schedule: Schedule)

    suspend fun getCalendarById(id: Long): Calendar?
    fun getAllCalendar(): Flow<List<Calendar>>
    suspend fun deleteCalendarById(id: Long)
    suspend fun insertCalendar(calendar: Calendar)
}