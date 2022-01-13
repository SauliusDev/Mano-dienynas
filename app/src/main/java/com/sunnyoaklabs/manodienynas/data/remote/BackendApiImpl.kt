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
import com.sunnyoaklabs.manodienynas.data.remote.dto.*
import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendarDto
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWorkDto
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWorkDto
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWorkDto
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class BackendApiImpl() : BackendApi {

    override suspend fun getEvents(): Document {
        return Jsoup.connect(EVENTS_GET).get()
    }

    override suspend fun getMarks(): Document {
        return Jsoup.connect(MARKS_GET).get()
    }

    override suspend fun getAttendance(): Document {
        return Jsoup.connect(ATTENDANCE_GET).get()
    }

    override suspend fun getClassWork(): Document {
        return Jsoup.connect(CLASS_WORK_GET).get()
    }

    // todo
    override suspend fun postClassWork(payload: PostClassWorkDto): Document {
        return Jsoup.connect(CLASS_WORK_POST).post()
    }

    override suspend fun getHomeWork(): Document {
        return Jsoup.connect(HOME_WORK_GET).get()
    }

    // todo
    override suspend fun postHomeWork(payload: PostHomeWorkDto): Document {
        return Jsoup.connect(HOME_WORK_GET).post()
    }

    override suspend fun getControlWork(): Document {
        return Jsoup.connect(CONTROL_WORK_GET).get()
    }

    // todo
    override suspend fun postControlWork(payload: PostControlWorkDto): Document {
        return Jsoup.connect(CONTROL_WORK_POST).post()
    }

    override suspend fun getTerm(): Document {
        return Jsoup.connect(TERM_GET).get()
    }

    override suspend fun getTermLegend(): Document {
        return Jsoup.connect(TERM_LEGEND_GET).get()
    }

    override suspend fun getMessagesGotten(): Document {
        return Jsoup.connect(MESSAGE_GOTTEN_LIST_GET).get()
    }

    override suspend fun getMessagesSent(): Document {
        return Jsoup.connect(MESSAGE_SENT_LIST_GET).get()
    }

    override suspend fun getMessagesFavorite(): Document {
        return Jsoup.connect(MESSAGE_STARRED_LIST_GET).get()
    }

    override suspend fun getMessagesDeleted(): Document {
        return Jsoup.connect(MESSAGE_DELETED_LIST_GET).get()
    }

    override suspend fun getMessageIndividual(): Document {
        return Jsoup.connect(MESSAGE_INDIVIDUAL_GET).get()
    }

    override suspend fun getHoliday(): Document {
        return Jsoup.connect(HOLIDAY_GET).get()
    }

    override suspend fun getParentMeetings(): Document {
        return Jsoup.connect(PARENT_MEETINGS_GET).get()
    }

    override suspend fun getSchedule(): Document {
        return Jsoup.connect(SCHEDULE_GET).get()
    }

    override suspend fun getCalendar(): Document {
        return Jsoup.connect(CALENDAR_GET).get()
    }

    override suspend fun getCalendarDate(payload: GetCalendarDto): Document {
        return Jsoup.connect(CALENDAR_DATE_GET).get()
    }


}