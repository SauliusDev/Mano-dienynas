package com.sunnyoaklabs.manodienynas.data.remote

import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendar
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWork
import com.sunnyoaklabs.manodienynas.data.util.Converter
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import org.jsoup.nodes.Document
import javax.inject.Inject

interface BackendApi {

    suspend fun postLogin(credentials: Credentials): Document

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

    companion object {
        fun create(converter: Converter): BackendApi {
            return BackendApiImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                },
                converter = converter
            )
        }
    }
}