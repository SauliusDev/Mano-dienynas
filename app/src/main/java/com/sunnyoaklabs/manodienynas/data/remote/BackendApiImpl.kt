package com.sunnyoaklabs.manodienynas.data.remote

import com.sunnyoaklabs.manodienynas.data.util.Converter
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
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MARKS_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_DELETED_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_GOTTEN_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_INDIVIDUAL_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_SENT_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_STARRED_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.PARENT_MEETINGS_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.SCHEDULE_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.TERM_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.TERM_LEGEND_GET
import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendar
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWork
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.jsoup.nodes.Document
import javax.inject.Inject


class BackendApiImpl @Inject constructor(
    private val converter: Converter,
    private val client: HttpClient
) : BackendApi {

    override suspend fun getEvents(): Document {
        return client.get { url(EVENTS_GET) }
    }

    override suspend fun getMarks(): Document {
        return client.get { url(MARKS_GET) }
    }

    override suspend fun getAttendance(): Document {
        return client.get { url(ATTENDANCE_GET) }
    }

    override suspend fun getClassWork(): Document {
        return client.get { url(CLASS_WORK_GET) }
    }

    override suspend fun postClassWork(payload: PostClassWork): Document {
        return client.post {
            url(CLASS_WORK_POST)
            contentType(ContentType.Application.Json)
            body = converter.toPostClassWorkJson(payload)
        }
    }

    override suspend fun getHomeWork(): Document {
        return client.get { url(HOME_WORK_GET) }
    }

    override suspend fun postHomeWork(payload: PostHomeWork): Document {
        return client.post {
            url(HOME_WORK_POST)
            contentType(ContentType.Application.Json)
            body = converter.toPostHomeWorkJson(payload)
        }
    }

    override suspend fun getControlWork(): Document {
        return client.get { url(CONTROL_WORK_GET) }
    }

    override suspend fun postControlWork(payload: PostControlWork): Document {
        return client.post {
            url(CONTROL_WORK_POST)
            contentType(ContentType.Application.Json)
            body = converter.toPostControlWorkJson(payload)
        }
    }

    override suspend fun getTerm(): Document {
        return client.get { url(TERM_GET) } 
    }

    override suspend fun getTermLegend(): Document {
        return client.get { url(TERM_LEGEND_GET) }
    }

    override suspend fun getMessagesGotten(): Document {
        return client.get { url(MESSAGE_GOTTEN_LIST_GET) }
    }

    override suspend fun getMessagesSent(): Document {
        return client.get { url(MESSAGE_SENT_LIST_GET) }
    }

    override suspend fun getMessagesStarred(): Document {
        return client.get { url(MESSAGE_STARRED_LIST_GET) }
    }

    override suspend fun getMessagesDeleted(): Document {
        return client.get { url(MESSAGE_DELETED_LIST_GET) }
    }

    override suspend fun getMessageIndividual(): Document {
        return client.get { url(MESSAGE_INDIVIDUAL_GET) }
    }

    override suspend fun getHoliday(): Document {
        return client.get { url(HOLIDAY_GET) }
    }

    override suspend fun getParentMeetings(): Document {
        return client.get { url(PARENT_MEETINGS_GET) }
    }

    override suspend fun getSchedule(): Document {
        return client.get { url(SCHEDULE_GET) }
    }

    override suspend fun getCalendar(): Document {
        return client.get { url(CALENDAR_GET) }
    }

    override suspend fun getCalendarDate(payload: GetCalendar): Document {
        return client.get { url(CALENDAR_DATE_GET) }
    }


}