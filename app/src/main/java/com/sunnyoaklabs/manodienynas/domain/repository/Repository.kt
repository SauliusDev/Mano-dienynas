package com.sunnyoaklabs.manodienynas.domain.repository

import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendar
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWork
import com.sunnyoaklabs.manodienynas.domain.model.*
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getEvents(): Flow<Resource<List<Event>>>

    fun getMarks(): Flow<Resource<List<Marks>>>

    fun getAttendance(): Flow<Resource<List<Attendance>>>

    fun getClassWork(): Flow<Resource<List<ClassWork>>>
    fun getClassWorkByCondition(payload: PostClassWork): Flow<Resource<List<ClassWork>>>

    fun getHomeWork(): Flow<Resource<List<HomeWork>>>
    fun getHomeWorkByCondition(payload: PostHomeWork): Flow<Resource<List<HomeWork>>>

    fun getControlWork(): Flow<Resource<List<ControlWork>>>
    fun getControlWorkByCondition(payload: PostControlWork): Flow<Resource<List<ControlWork>>>

    fun getTerm(): Flow<Resource<List<Term>>>

    fun getTermLegend(): Flow<Resource<TermLegend>>

    fun getMessagesGotten(): Flow<Resource<List<Message>>>

    fun getMessagesSent(): Flow<Resource<List<Message>>>

    fun getMessagesStarred(): Flow<Resource<List<Message>>>

    fun getMessagesDeleted(): Flow<Resource<List<Message>>>

    fun getMessageIndividual(): Flow<Resource<List<MessageIndividual>>>

    fun getHoliday(): Flow<Resource<List<Holiday>>>

    fun getParentMeetings(): Flow<Resource<List<ParentMeetings>>>

    fun getSchedule(): Flow<Resource<List<Schedule>>>

    fun getCalendar(): Flow<Resource<List<Calendar>>>
    fun getCalendarDate(payload: GetCalendar): Flow<Resource<List<Calendar>>>
    
}