package com.sunnyoaklabs.manodienynas.data.repository

import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import com.sunnyoaklabs.manodienynas.core.util.*
import com.sunnyoaklabs.manodienynas.core.util.Errors.NULL_OBJECT_RECEIVED_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.SESSION_COOKIE_EXPIRED
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.remote.BackendApi
import com.sunnyoaklabs.manodienynas.data.remote.dto.*
import com.sunnyoaklabs.manodienynas.data.util.Converter
import com.sunnyoaklabs.manodienynas.domain.model.*
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception

class RepositoryImpl(
    private val api: BackendApi,
    private val dataSource: DataSource,
    private val converter: Converter
) : Repository {

    override suspend fun getSettings(): Settings {
        val userSettings = dataSource.getSettings()
        return converter.toSettingsFromEntity(userSettings)
    }

    override suspend fun getSessionCookies(): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val credentials = getCredentials()
            val message = api.postLogin(
                PostLogin(
                    credentials.username,
                    credentials.password,
                    1
                ))
            if (message == SessionValidationJsonResponses.CREDENTIALS_CORRECT) emit(Resource.Success(message))
            else emit(Resource.Error(Errors.INCORRECT_CREDENTIALS))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override suspend fun getCredentials(): Credentials {
        val credentials = dataSource.getCredentials()
        return converter.toCredentialsFromEntity(credentials)
    }

    override fun getEvents(): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Loading())
        val eventsLocal = dataSource.getAllEvents().map { converter.toEventFromEntity(it) }
        emit(Resource.Loading(data = eventsLocal))
        try {
            val response = api.getEvents()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val eventsApi = converter.toEvents(response)
                dataSource.deleteAllEvents()
                eventsApi.forEach { dataSource.insertEvent(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newEvents = dataSource.getAllEvents().map { converter.toEventFromEntity(it) }
        emit(Resource.Success(newEvents))
    }

    override fun getMarks(): Flow<Resource<List<Mark>>> = flow {
        emit(Resource.Loading())
        val marksLocal = dataSource.getAllMarks().map { converter.toMarkFromEntity(it) }
        emit(Resource.Loading(data = marksLocal))
        try {
            val response = api.getMarks()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val marksApi = converter.toMarks(response)
                dataSource.deleteAllMarks()
                marksApi.forEach { dataSource.insertMark(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newMarks = dataSource.getAllMarks().map { converter.toMarkFromEntity(it) }
        emit(Resource.Success(newMarks))
    }

    override fun getAttendance(): Flow<Resource<List<Attendance>>> = flow {
        emit(Resource.Loading())
        val attendanceLocal = dataSource.getAllAttendances().map { converter.toAttendanceFromEntity(it) }
        emit(Resource.Loading(data = attendanceLocal))
        try {
            val response = api.getAttendance()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val attendanceApi = converter.toAttendance(response)
                dataSource.deleteAllAttendance()
                attendanceApi.forEach { dataSource.insertAttendance(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newAttendance = dataSource.getAllAttendances().map { converter.toAttendanceFromEntity(it) }
        emit(Resource.Success(newAttendance))
    }

    override fun getClassWork(): Flow<Resource<List<ClassWork>>> = flow {
        emit(Resource.Loading())
        val classWorkLocal = dataSource.getAllClassWorks().map { converter.toClassWorkFromEntity(it) }
        emit(Resource.Loading(data = classWorkLocal))
        try {
            val response = api.getClassWork()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val classWorkApi = converter.toClassWork(response)
                dataSource.deleteAllClassWork()
                classWorkApi.forEach { dataSource.insertClassWork(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newClassWork = dataSource.getAllClassWorks().map { converter.toClassWorkFromEntity(it) }
        emit(Resource.Success(newClassWork))
    }

    override fun getClassWorkByCondition(
        payload: PostClassWork,
        page: Int
    ): Flow<Resource<List<ClassWork>>> = flow {
        emit(Resource.Loading())
        val classWorkLocal = dataSource.getAllClassWorks().map { converter.toClassWorkFromEntity(it) }
        emit(Resource.Loading(data = classWorkLocal))
        try {
            val response = api.postClassWork(payload, page)
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val classWorkApi = converter.toClassWork(response)
                dataSource.deleteAllClassWork()
                classWorkApi.forEach { dataSource.insertClassWork(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newClassWork = dataSource.getAllClassWorks().map { converter.toClassWorkFromEntity(it) }
        emit(Resource.Success(newClassWork))
    }

    override fun getHomeWork(): Flow<Resource<List<HomeWork>>> = flow {
        emit(Resource.Loading())
        val homeWorkLocal = dataSource.getAllHomeWorks().map { converter.toHomeWorkFromEntity(it) }
        emit(Resource.Loading(data = homeWorkLocal))
        try {
            val response = api.getHomeWork()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val homeWorkApi = converter.toHomeWork(response)
                dataSource.deleteAllHomeWork()
                homeWorkApi.forEach { dataSource.insertHomeWork(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newHomeWork = dataSource.getAllHomeWorks().map { converter.toHomeWorkFromEntity(it) }
        emit(Resource.Success(newHomeWork))
    }

    override fun getHomeWorkByCondition(
        payload: PostHomeWork,
        page: Int
    ): Flow<Resource<List<HomeWork>>> = flow {
        emit(Resource.Loading())
        val homeWorkLocal = dataSource.getAllHomeWorks().map { converter.toHomeWorkFromEntity(it) }
        emit(Resource.Loading(data = homeWorkLocal))
        try {
            val response = api.postHomeWork(payload, page)
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val homeWorkApi = converter.toHomeWork(response)
                dataSource.deleteAllHomeWork()
                homeWorkApi.forEach { dataSource.insertHomeWork(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newHomeWork = dataSource.getAllHomeWorks().map { converter.toHomeWorkFromEntity(it) }
        emit(Resource.Success(newHomeWork))
    }

    override fun getControlWork(): Flow<Resource<List<ControlWork>>> = flow {
        emit(Resource.Loading())
        val controlWorkLocal = dataSource.getAllControlWorks().map { converter.toControlWorkFromEntity(it) }
        emit(Resource.Loading(data = controlWorkLocal))
        try {
            val response = api.getControlWork()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val controlWorkApi = converter.toControlWork(response)
                dataSource.deleteAllControlWork()
                controlWorkApi.forEach { dataSource.insertControlWork(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newControlWork = dataSource.getAllControlWorks().map { converter.toControlWorkFromEntity(it) }
        emit(Resource.Success(newControlWork))
    }

    override fun getControlWorkByCondition(
        payload: PostControlWork,
        page: Int
    ): Flow<Resource<List<ControlWork>>> = flow {
        emit(Resource.Loading())
        val controlWorkLocal = dataSource.getAllControlWorks().map { converter.toControlWorkFromEntity(it) }
        emit(Resource.Loading(data = controlWorkLocal))
        try {
            val response = api.postControlWork(payload)
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val controlWorkApi = converter.toControlWork(response)
                dataSource.deleteAllControlWork()
                controlWorkApi.forEach { dataSource.insertControlWork(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newControlWorks = dataSource.getAllControlWorks().map { converter.toControlWorkFromEntity(it) }
        emit(Resource.Success(newControlWorks))
    }

    override fun getTerm(): Flow<Resource<List<Term>>> = flow {
        emit(Resource.Loading())
        val termsLocal = dataSource.getAllTerms().map { converter.toTermFromEntity(it) }
        emit(Resource.Loading(data = termsLocal))
        try {
            val response = api.getTerm()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val termApi = converter.toTerm(response)
                dataSource.deleteAllTerm()
                termApi.forEach { dataSource.insertTerm(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newTerms = dataSource.getAllTerms().map { converter.toTermFromEntity(it) }
        emit(Resource.Success(newTerms))
    }

    override fun getMessagesGotten(): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesGottenLocal = dataSource.getAllMessagesGotten().map { converter.toMessageGottenFromEntity(it) }
        emit(Resource.Loading(data = messagesGottenLocal))
        try {
            val response = api.getMessagesGotten()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val messagesGottenApi = converter.toMessages(response)
                dataSource.deleteAllMessageGotten()
                messagesGottenApi.forEach { dataSource.insertMessageGotten(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newMessagesGotten = dataSource.getAllMessagesGotten().map { converter.toMessageGottenFromEntity(it) }
        emit(Resource.Success(newMessagesGotten))
    }

    override fun getMessagesSent(): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesSentLocal = dataSource.getAllMessagesSent().map { converter.toMessageSentFromEntity(it) }
        emit(Resource.Loading(data = messagesSentLocal))
        try {
            val response = api.getMessagesSent()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val messagesSentApi = converter.toMessages(response)
                dataSource.deleteAllMessageSent()
                messagesSentApi.forEach { dataSource.insertMessageSent(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newMessagesSent = dataSource.getAllMessagesSent().map { converter.toMessageSentFromEntity(it) }
        emit(Resource.Success(newMessagesSent))
    }

    override fun getMessagesStarred(): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesStarredLocal = dataSource.getAllMessagesStarred().map { converter.toMessageStarredFromEntity(it) }
        emit(Resource.Loading(data = messagesStarredLocal))
        try {
            val response = api.getMessagesStarred()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val messagesStarredApi = converter.toMessages(response)
                dataSource.deleteAllMessageStarred()
                messagesStarredApi.forEach { dataSource.insertMessageStarred(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newMessagesStarred = dataSource.getAllMessagesStarred().map { converter.toMessageStarredFromEntity(it) }
        emit(Resource.Success(newMessagesStarred))
    }

    override fun getMessagesDeleted(): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesDeletedLocal = dataSource.getAllMessagesDeleted().map { converter.toMessageDeletedFromEntity(it) }
        emit(Resource.Loading(data = messagesDeletedLocal))
        try {
            val response = api.getMessagesDeleted()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val messagesDeletedApi = converter.toMessages(response)
                dataSource.deleteAllMessageDeleted()
                messagesDeletedApi.forEach { dataSource.insertMessageDeleted(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newMessagesDeleted = dataSource.getAllMessagesDeleted().map { converter.toMessageDeletedFromEntity(it) }
        emit(Resource.Success(newMessagesDeleted))
    }

    override fun getMessageIndividual(id: String): Flow<Resource<MessageIndividual>> = flow {
        emit(Resource.Loading())
        val messageIndividualLocal = converter.toMessageIndividualFromEntity(dataSource.getMessageIndividualById(id.toLong()))
        emit(Resource.Loading(data = messageIndividualLocal))
        try {
            val response = api.getMessageIndividual(id)
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val messagesIndividualApi = converter.toMessagesIndividual(response)
                dataSource.deleteMessageIndividualById(id.toLong())
                dataSource.insertMessageIndividual(messagesIndividualApi)
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newMessagesIndividual = converter.toMessageIndividualFromEntity(dataSource.getMessageIndividualById(id.toLong()))
        emit(Resource.Success(newMessagesIndividual))
    }

    override fun getHoliday(): Flow<Resource<List<Holiday>>> = flow {
        emit(Resource.Loading())
        val holidayLocal = dataSource.getAllHolidays().map { converter.toHolidayFromEntity(it) }
        emit(Resource.Loading(data = holidayLocal))
        try {
            val response = api.getHoliday()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val holidayApi = converter.toHoliday(response)
                dataSource.deleteAllHoliday()
                holidayApi.forEach { dataSource.insertHoliday(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newHoliday = dataSource.getAllHolidays().map { converter.toHolidayFromEntity(it) }
        emit(Resource.Success(newHoliday))
    }

    override fun getParentMeetings(): Flow<Resource<List<ParentMeeting>>> = flow {
        emit(Resource.Loading())
        val parentMeetingsLocal = dataSource.getAllParentMeetings().map { converter.toParentMeetingFromEntity(it) }
        emit(Resource.Loading(data = parentMeetingsLocal))
        try {
            val response = api.getParentMeetings()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val parentMeetingsApi = converter.toParentMeeting(response)
                dataSource.deleteAllParentMeeting()
                parentMeetingsApi.forEach { dataSource.insertParentMeeting(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newParentMeetings = dataSource.getAllParentMeetings().map { converter.toParentMeetingFromEntity(it) }
        emit(Resource.Success(newParentMeetings))
    }

    override fun getSchedule(): Flow<Resource<List<Schedule>>> = flow {
        emit(Resource.Loading())
        val scheduleLocal = dataSource.getAllSchedule().map { converter.toScheduleFromEntity(it) }
        emit(Resource.Loading(data = scheduleLocal))
        try {
            val response = api.getSchedule()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val scheduleApi = converter.toSchedule(response)
                dataSource.deleteAllMarks()
                scheduleApi.forEach { dataSource.insertSchedule(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newSchedule = dataSource.getAllSchedule().map { converter.toScheduleFromEntity(it) }
        emit(Resource.Success(newSchedule))
    }

    override fun getCalendar(): Flow<Resource<List<Calendar>>> = flow {
        emit(Resource.Loading())
        val calendarLocal = dataSource.getAllCalendars().map { converter.toCalendarFromEntity(it) }
        emit(Resource.Loading(data = calendarLocal))
        try {
            val response = api.getCalendar()
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val calendarApi = converter.toCalendar(response)
                dataSource.deleteAllCalendar()
                calendarApi.forEach { dataSource.insertCalendar(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newCalendar = dataSource.getAllCalendars().map { converter.toCalendarFromEntity(it) }
        emit(Resource.Success(newCalendar))
    }

    override fun getCalendarDate(payload: GetCalendar): Flow<Resource<List<Calendar>>> = flow {
        emit(Resource.Loading())
        val calendarDateLocal = dataSource.getAllCalendars().map { converter.toCalendarFromEntity(it) }
        emit(Resource.Loading(data = calendarDateLocal))
        try {
            val response = api.getCalendarDate(payload)
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val calendarDateApi = converter.toCalendar(response)
                dataSource.deleteAllMarks()
                calendarDateApi.forEach { dataSource.insertCalendar(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newCalendar = dataSource.getAllCalendars().map { converter.toCalendarFromEntity(it) }
        emit(Resource.Success(newCalendar))
    }

    override fun getCalendarEvent(id: String): Flow<Resource<List<Calendar>>> = flow {
        emit(Resource.Loading())
        val calendarEventLocal = dataSource.getAllCalendars().map { converter.toCalendarFromEntity(it) }
        emit(Resource.Loading(data = calendarEventLocal))
        try {
            val response = api.getCalendarEvent(id)
            val user = converter.toUser(response)
            if (user.name.isNotBlank()) {
                dataSource.deleteUser()
                dataSource.insertUser(user)
                val calendarEventApi = converter.toCalendar(response)
                dataSource.deleteAllCalendar()
                calendarEventApi.forEach { dataSource.insertCalendar(it) }
            } else {
                emit(Resource.Error(SESSION_COOKIE_EXPIRED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
        val newCalendarEvent = dataSource.getAllCalendars().map { converter.toCalendarFromEntity(it) }
        emit(Resource.Success(newCalendarEvent))
    }


}