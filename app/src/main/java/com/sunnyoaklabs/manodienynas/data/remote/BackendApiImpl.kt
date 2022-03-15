package com.sunnyoaklabs.manodienynas.data.remote

import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.ATTENDANCE_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CALENDAR_EVENT_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CALENDAR_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CHANGE_ROLE_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CLASS_WORK_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CLASS_WORK_POST
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CONTROL_WORK_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.CONTROL_WORK_POST
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.EVENTS_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.EVENTS_INDIVIDUAL_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.EVENTS_POST
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.HOLIDAY_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.HOME_WORK_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.HOME_WORK_POST
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.LOGIN_POST
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.LOGOUT_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MARKS_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MARKS_POST
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_DELETED_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_DELETED_LIST_PAGE_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_GOTTEN_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_GOTTEN_LIST_PAGE_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_INDIVIDUAL_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_SENT_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_SENT_LIST_PAGE_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_STARRED_LIST_GET
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes.MESSAGE_STARRED_LIST_PAGE_GET
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

    override suspend fun getLogout(): String {
        return client.get { url(LOGOUT_GET) }
    }

    override suspend fun getChangeRole(schoolId: String): String {
        return client.get { url(CHANGE_ROLE_GET.replace("{school_id}", schoolId)) }
    }

    override suspend fun postEvents(postEvents: PostEvents): String {
        return client.post {
            url(EVENTS_POST)
            body = FormDataContent(Parameters.build {
                append("evCount[]", postEvents.evCount1.toString())
                append("evCount[]", postEvents.evCount2.toString())
                for (i in postEvents.shownEvents.indices) {
                    append("shownEvents[]", postEvents.shownEvents[i])
                }
            })
        }
    }

    override suspend fun getEvents(): String {
        return client.get { url(EVENTS_GET) }
    }

    override suspend fun getMarks(): String {
        return client.get { url(MARKS_GET) }
    }

    override suspend fun postMarks(payload: PostMarks, schoolId: String): String {
        return client.post {
            url(MARKS_POST.replace("{school_id}", schoolId))
            body = FormDataContent(Parameters.build {
                append("datepickerFrom", payload.dateFrom)
                append("datepickerTo", payload.dateTo)
                append("changeDate", payload.changeDate)
                append("urlBase", payload.urlBase)
                append("classId", payload.classId.toString())
                append("print", payload.print.toString())
            })
        }
    }

    override suspend fun getMarksEvent(infoUrl: String): String {
        return client.get { url(EVENTS_INDIVIDUAL_GET.replace("{mark_url}", infoUrl)) }
    }

    override suspend fun getAttendance(): String {
        return client.get { url(ATTENDANCE_GET) }
    }

    override suspend fun getClassWork(): String {
        return client.get { url(CLASS_WORK_GET) }
    }

    override suspend fun postClassWork(payload: PostClassWork, page: Int): String {
        return client.post {
            url(CLASS_WORK_POST.replace("{page}", page.toString()))
            body = FormDataContent(Parameters.build {
                append("datepickerFrom", payload.dateFrom)
                append("datepickerTo", payload.dateTo)
                append("changeDate", payload.changeDate)
                append("orderBy", payload.orderBy.toString())
                append("lessonSelect", payload.lessonSelect.toString())
            })
        }
    }

    override suspend fun getHomeWork(): String {
        return client.get { url(HOME_WORK_GET) }
    }

    override suspend fun postHomeWork(payload: PostHomeWork, page: Int): String {
        return client.post {
            url(HOME_WORK_POST.replace("{page}", page.toString()))
            body = FormDataContent(Parameters.build {
                append("datepickerFrom", payload.dateFrom)
                append("datepickerTo", payload.dateTo)
                append("changeDate", payload.changeDate)
                append("orderBy", payload.orderBy.toString())
                append("lessonSelect", payload.lessonSelect.toString())
            })
        }
    }

    override suspend fun getControlWork(): String {
        return client.get { url(CONTROL_WORK_GET) }
    }

    override suspend fun postControlWork(payload: PostControlWork): String {
        return client.post {
            url(CONTROL_WORK_POST.replace("/{group_id}", ""))
            body = FormDataContent(Parameters.build {
                append("datepickerFrom", payload.dateFrom)
                append("datepickerTo", payload.dateTo)
                append("changeDate", payload.changeDate)
                append("selectGroup", payload.selectedGroup.toString())
            })
        }
    }

    override suspend fun getTerm(): String {
        return client.get { url(TERM_GET) }
    }

    override suspend fun getMessagesGotten(): String {
        return client.get { url(MESSAGE_GOTTEN_LIST_GET) }
    }

    override suspend fun getMessagesGottenByCondition(page: String): String {
        return client.get { url(MESSAGE_GOTTEN_LIST_PAGE_GET.replace("{page}", page)) }
    }

    override suspend fun getMessagesSent(): String {
        return client.get { url(MESSAGE_SENT_LIST_GET) }
    }

    override suspend fun getMessagesSentByCondition(page: String): String {
        return client.get { url(MESSAGE_SENT_LIST_PAGE_GET.replace("{page}", page)) }
    }

    override suspend fun getMessagesStarred(): String {
        return client.get { url(MESSAGE_STARRED_LIST_GET) }
    }

    override suspend fun getMessagesStarredByCondition(page: String): String {
        return client.get { url(MESSAGE_STARRED_LIST_PAGE_GET.replace("{page}", page)) }
    }

    override suspend fun getMessagesDeleted(): String {
        return client.get { url(MESSAGE_DELETED_LIST_GET) }
    }

    override suspend fun getMessagesDeletedByCondition(page: String): String {
        return client.get { url(MESSAGE_DELETED_LIST_PAGE_GET.replace("{page}", page)) }
    }

    override suspend fun getMessageIndividual(id: String): String {
        return client.get { url(MESSAGE_INDIVIDUAL_GET.replace("{message_id}", id)) }
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

    override suspend fun getCalendarDate(payload: GetCalendarDto): String {
        return client.get { url(CALENDAR_GET) }
    }

    override suspend fun getCalendarEvent(id: String): String {
        return client.get { url(CALENDAR_EVENT_GET.replace("{event_id}", id)) }
    }

}