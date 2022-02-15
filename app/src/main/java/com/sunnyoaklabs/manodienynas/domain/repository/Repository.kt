package com.sunnyoaklabs.manodienynas.domain.repository

import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendar
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWork
import com.sunnyoaklabs.manodienynas.domain.model.*
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getSettings(): Settings

    suspend fun getSessionCookies(): Flow<Resource<String>>

    suspend fun getCredentials(): Credentials

    fun getEvents(): Flow<Resource<String>>

    fun getMarks(): Flow<Resource<String>>

    fun getAttendance(): Flow<Resource<String>>

    fun getClassWork(): Flow<Resource<String>>
    fun getClassWorkByCondition(payload: PostClassWork): Flow<Resource<String>>

    fun getHomeWork(): Flow<Resource<String>>
    fun getHomeWorkByCondition(payload: PostHomeWork): Flow<Resource<String>>

    fun getControlWork(): Flow<Resource<String>>
    fun getControlWorkByCondition(payload: PostControlWork): Flow<Resource<String>>

    fun getTerm(): Flow<Resource<String>>

    fun getTermLegend(): Flow<Resource<String>>

    fun getMessagesGotten(): Flow<Resource<String>>

    fun getMessagesSent(): Flow<Resource<String>>

    fun getMessagesStarred(): Flow<Resource<String>>

    fun getMessagesDeleted(): Flow<Resource<String>>

    fun getMessageIndividual(): Flow<Resource<String>>

    fun getHoliday(): Flow<Resource<String>>

    fun getParentMeetings(): Flow<Resource<String>>

    fun getSchedule(): Flow<Resource<String>>

    fun getCalendar(): Flow<Resource<String>>
    fun getCalendarDate(payload: GetCalendar): Flow<Resource<String>>
    
}