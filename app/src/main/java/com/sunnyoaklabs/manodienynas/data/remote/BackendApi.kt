package com.sunnyoaklabs.manodienynas.data.remote

import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendar
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWork
import org.jsoup.nodes.Document

interface BackendApi {

    suspend fun getEvents(): Document

    suspend fun getMarks(): Document

    suspend fun getAttendance(): Document

    suspend fun getClassWork(): Document
    suspend fun postClassWork(payload: PostClassWork): Document

    suspend fun getHomeWork(): Document
    suspend fun postHomeWork(payload: PostHomeWork): Document

    suspend fun getControlWork(): Document
    suspend fun postControlWork(payload: PostControlWork): Document

    suspend fun getTerm(): Document

    suspend fun getTermLegend(): Document

    suspend fun getMessagesGotten(): Document

    suspend fun getMessagesSent(): Document

    suspend fun getMessagesStarred(): Document

    suspend fun getMessagesDeleted(): Document

    suspend fun getMessageIndividual(): Document

    suspend fun getHoliday(): Document

    suspend fun getParentMeetings(): Document

    suspend fun getSchedule(): Document

    suspend fun getCalendar(): Document
    suspend fun getCalendarDate(payload: GetCalendar): Document
}