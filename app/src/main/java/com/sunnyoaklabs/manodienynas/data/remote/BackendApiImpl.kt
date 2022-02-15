package com.sunnyoaklabs.manodienynas.data.remote

import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.ATTENDANCE_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CALENDAR_DATE_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CALENDAR_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CLASS_WORK_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CLASS_WORK_POST
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CONTROL_WORK_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CONTROL_WORK_POST
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.EVENTS_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.HOLIDAY_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.HOME_WORK_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.HOME_WORK_POST
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.LOGIN_POST
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MARKS_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_DELETED_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_GOTTEN_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_INDIVIDUAL_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_SENT_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_STARRED_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.PARENT_MEETINGS_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.SCHEDULE_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.TERM_GET
import com.sunnyoaklabs.manodienynas.data.remote.dto.*
import com.sunnyoaklabs.manodienynas.data.util.Converter
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class BackendApiImpl(
    private val converter: Converter,
    private val client: HttpClient
) : BackendApi {

    override suspend fun postLogin(payload: PostLogin): String {
        return client.post {
            url(LOGIN_POST)
            body = FormDataContent(Parameters.build {
                append("username", payload.username)
                append("password", payload.password)
                append("dienynas_remember_me", payload.dienynas_remember_me.toString())
            })
        }
    }

    override suspend fun getEvents(): String {
        return client.get { url(EVENTS_GET) }
    }

    override suspend fun getMarks(): String {
        return client.get { url(MARKS_GET) }
    }

    override suspend fun getAttendance(): String {
        return client.get { url(ATTENDANCE_GET) }
    }

    override suspend fun getClassWork(): String {
        return client.get { url(CLASS_WORK_GET) }
    }

    override suspend fun postClassWork(payload: PostClassWork): String {
        return client.post {
            url(CLASS_WORK_POST)
            contentType(ContentType.Application.FormUrlEncoded)
            body = converter.toPostClassWorkJson(payload)
        }
    }

    override suspend fun getHomeWork(): String {
        return client.get { url(HOME_WORK_GET) }
    }

    override suspend fun postHomeWork(payload: PostHomeWork): String {
        return client.post {
            url(HOME_WORK_POST)
            contentType(ContentType.Application.FormUrlEncoded)
            body = converter.toPostHomeWorkJson(payload)
        }
    }

    override suspend fun getControlWork(): String {
        return client.get { url(CONTROL_WORK_GET) }
    }

    override suspend fun postControlWork(payload: PostControlWork): String {
        return client.post {
            url(CONTROL_WORK_POST)
            contentType(ContentType.Application.FormUrlEncoded)
            body = converter.toPostControlWorkJson(payload)
        }
    }

    override suspend fun getTerm(): String {
        return client.get { url(TERM_GET) }
    }

    override suspend fun getMessagesGotten(): String {
        return client.get { url(MESSAGE_GOTTEN_LIST_GET) }
    }

    override suspend fun getMessagesSent(): String {
        return client.get { url(MESSAGE_SENT_LIST_GET) }
    }

    override suspend fun getMessagesStarred(): String {
        return client.get { url(MESSAGE_STARRED_LIST_GET) }
    }

    override suspend fun getMessagesDeleted(): String {
        return client.get { url(MESSAGE_DELETED_LIST_GET) }
    }

    override suspend fun getMessageIndividual(): String {
        return client.get { url(MESSAGE_INDIVIDUAL_GET) }
    }

    override suspend fun getHoliday(): String {
        return client.get { url(HOLIDAY_GET) }
    }

    override suspend fun getParentMeetings(): String {
        return client.get { url(PARENT_MEETINGS_GET) }
    }

    override suspend fun getSchedule(): String {
        return client.get { url(SCHEDULE_GET) }
    }

    override suspend fun getCalendar(): String {
        return client.get { url(CALENDAR_GET) }
    }

    override suspend fun getCalendarDate(payload: GetCalendar): String {
        return client.get { url(CALENDAR_DATE_GET) }
    }

}