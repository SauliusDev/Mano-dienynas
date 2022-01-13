package com.sunnyoaklabs.manodienynas.data.remote

import com.sunnyoaklabs.manodienynas.data.remote.dto.*
import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendarDto
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWorkDto
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWorkDto
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWorkDto
import org.jsoup.nodes.Document

interface BackendApi {

    suspend fun getEvents(): Document

    suspend fun getMarks(): Document

    suspend fun getAttendance(): Document

    suspend fun getClassWork(): Document
    suspend fun postClassWork(payload: PostClassWorkDto): Document

    suspend fun getHomeWork(): Document
    suspend fun postHomeWork(payload: PostHomeWorkDto): Document

    suspend fun getControlWork(): Document
    suspend fun postControlWork(payload: PostControlWorkDto): Document

    suspend fun getTerm(): Document

    suspend fun getTermLegend(): Document

    suspend fun getMessagesGotten(): Document

    suspend fun getMessagesSent(): Document

    suspend fun getMessagesFavorite(): Document

    suspend fun getMessagesDeleted(): Document

    suspend fun getMessageIndividual(): Document

    suspend fun getHoliday(): Document

    suspend fun getParentMeetings(): Document

    suspend fun getSchedule(): Document

    suspend fun getCalendar(): Document
    suspend fun getCalendarDate(payload: GetCalendarDto): Document
}