package com.sunnyoaklabs.manodienynas.domain.repository

import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.data.remote.dto.*
import com.sunnyoaklabs.manodienynas.domain.model.*
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getSettings(): Flow<Resource<Settings>>

    suspend fun getSessionCookies(): Flow<Resource<String>>

    suspend fun getCredentials(): Credentials

    suspend fun getPerson(): Person

    fun getEventsPage(): Flow<Resource<List<Event>>>

    fun getEvents(): Flow<Resource<List<Event>>>

    fun getMarks(): Flow<Resource<List<Mark>>>
    fun getMarksByCondition(payload: PostMarks): Flow<Resource<List<Mark>>>
    fun getMarksEventItem(infoUrl: String): Flow<Resource<MarksEventItem>>

    fun getAttendance(): Flow<Resource<List<Attendance>>>

    fun getClassWork(): Flow<Resource<List<ClassWork>>>
    fun getClassWorkByCondition(payload: PostClassWork, page: Int): Flow<Resource<List<ClassWork>>>

    fun getHomeWork(): Flow<Resource<List<HomeWork>>>
    fun getHomeWorkByCondition(payload: PostHomeWork, page: Int): Flow<Resource<List<HomeWork>>>

    fun getControlWork(): Flow<Resource<List<ControlWork>>>
    fun getControlWorkByCondition(payload: PostControlWork, page: Int): Flow<Resource<List<ControlWork>>>

    fun getTerm(): Flow<Resource<List<Term>>>

    fun getMessagesGotten(): Flow<Resource<List<Message>>>
    fun getMessagesGottenByCondition(page: Int): Flow<Resource<List<Message>>>

    fun getMessagesSent(): Flow<Resource<List<Message>>>
    fun getMessagesSentByCondition(page: Int): Flow<Resource<List<Message>>>

    fun getMessagesStarred(): Flow<Resource<List<Message>>>
    fun getMessagesStarredByCondition(page: Int): Flow<Resource<List<Message>>>

    fun getMessagesDeleted(): Flow<Resource<List<Message>>>
    fun getMessagesDeletedByCondition(page: Int): Flow<Resource<List<Message>>>

    fun getMessageIndividual(id: String): Flow<Resource<MessageIndividual>>

    fun getHoliday(): Flow<Resource<List<Holiday>>>

    fun getParentMeetings(): Flow<Resource<List<ParentMeeting>>>

    fun getSchedule(): Flow<Resource<List<Schedule>>>

    fun getCalendar(payload: GetCalendarDto): Flow<Resource<List<Calendar>>>

    fun getCalendarEvent(url: String): Flow<Resource<CalendarEvent>>

}