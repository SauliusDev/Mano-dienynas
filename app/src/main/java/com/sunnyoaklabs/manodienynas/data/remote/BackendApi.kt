package com.sunnyoaklabs.manodienynas.data.remote

import com.sunnyoaklabs.manodienynas.data.remote.dto.*
import com.sunnyoaklabs.manodienynas.data.util.Converter
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface BackendApi {

    suspend fun postLogin(payload: PostLogin): String

    suspend fun getLogout(): String

    suspend fun getChangeRole(schoolId: String): String

    suspend fun postEvents(postEvents: PostEvents): String

    suspend fun getEvents(): String

    suspend fun getMarks(): String

    suspend fun getAttendance(): String

    suspend fun getClassWork(): String
    suspend fun postClassWork(payload: PostClassWork, page: Int): String

    suspend fun getHomeWork(): String
    suspend fun postHomeWork(payload: PostHomeWork, page: Int): String

    suspend fun getControlWork(): String
    suspend fun postControlWork(payload: PostControlWork): String

    suspend fun getTerm(): String

    suspend fun getMessagesGotten(): String

    suspend fun getMessagesSent(): String

    suspend fun getMessagesStarred(): String

    suspend fun getMessagesDeleted(): String

    suspend fun getMessageIndividual(id: String): String

    //suspend fun getMessageDelete(id: String): String
    //suspend fun getMessageReply(id: String): String
    //suspend fun getMessageForward(id: String): String

    suspend fun getHoliday(): String

    suspend fun getParentMeetings(): String

    suspend fun getSchedule(): String

    suspend fun getCalendarDate(payload: GetCalendarDto): String

    suspend fun getCalendarEvent(id: String): String

    companion object {
        fun create(converter: Converter): BackendApi {
            return BackendApiImpl(
                client = HttpClient(Android) {
//                    install(Logging) {
//                        level = LogLevel.ALL
//                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                    install(HttpCookies) {
                        storage = AcceptAllCookiesStorage()
                    }
                    install(HttpTimeout) {
                        requestTimeoutMillis = 150000L
                        connectTimeoutMillis = 150000L
                        socketTimeoutMillis = 150000L
                    }
                },
                converter = converter
            )
        }
    }
}