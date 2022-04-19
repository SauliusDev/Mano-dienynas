package com.sunnyoaklabs.manodienynas.data.repository

import android.util.Log
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.SESSION_COOKIE_EXPIRED
import com.sunnyoaklabs.manodienynas.core.util.Errors.TIMEOUT_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.SessionValidationJsonResponses
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.remote.BackendApi
import com.sunnyoaklabs.manodienynas.data.remote.dto.*
import com.sunnyoaklabs.manodienynas.data.util.Converter
import com.sunnyoaklabs.manodienynas.domain.model.*
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import io.ktor.client.features.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class RepositoryImpl(
    private val api: BackendApi,
    private val dataSource: DataSource,
    private val converter: Converter
) : Repository {

    override fun getSettings(): Flow<Resource<Settings>> = flow {
        emit(Resource.Loading())
        val settings = dataSource.getSettings()
        emit(Resource.Success(converter.toSettingsFromEntity(settings)))
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
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override suspend fun getCredentials(): Credentials {
        val credentials = dataSource.getCredentials()
        return converter.toCredentialsFromEntity(credentials)
    }

    override suspend fun getPerson(): Person {
        return converter.toPersonFromEntity(dataSource.getPerson())
    }

    override fun getEventsPage(): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Loading())
        val eventsLocal = dataSource.getAllEvents().map { converter.toEventFromEntity(it) }
        try {
            val shownEvents = mutableListOf<String>()
            for (i in eventsLocal.indices) {
                shownEvents.add(eventsLocal[i].event_id)
            }
            val postEvents = PostEvents(eventsLocal.size, 0, shownEvents)
            val response = api.postEvents(postEvents)
            val eventsApi = converter.toEvents(response)
            eventsApi.forEach { dataSource.insertEvent(it) }
            var newEvents = dataSource.getAllEvents().map { converter.toEventFromEntity(it) }
            newEvents = newEvents.subList(eventsLocal.size, newEvents.size)
            emit(Resource.Success(newEvents))
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getEvents(): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Loading())
        val eventsLocal = dataSource.getAllEvents().map { converter.toEventFromEntity(it) }
        emit(Resource.Loading(data = eventsLocal))
        try {
            val response = api.getEvents()
            when (val person = converter.toPerson(response)) {
                is Resource.Success -> {
                    val settingsLocal = converter.toSettingsFromEntity(dataSource.getSettings())
                    if (settingsLocal.selectedSchool == null) {
                        val settings = Settings(
                            settingsLocal.keepSignedIn,
                            (person.data ?: Person("", "", emptyList())).schoolsNames[0]
                        )
                        dataSource.deleteSettings()
                        dataSource.insertSettings(settings)
                    }
                    dataSource.deletePerson()
                    dataSource.insertPerson(person.data ?: Person("", "", emptyList()))
                    val eventsApi = converter.toEvents(response)
                    dataSource.deleteAllEvents()
                    eventsApi.forEach { dataSource.insertEvent(it) }
                    val newEvents = dataSource.getAllEvents().map { converter.toEventFromEntity(it) }
                    emit(Resource.Success(newEvents))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMarks(): Flow<Resource<List<Mark>>> = flow {
        emit(Resource.Loading())
        val marksLocal = dataSource.getAllMarks().map { converter.toMarkFromEntity(it) }
        emit(Resource.Loading(data = marksLocal))
        try {
            val response = api.getMarks()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val marksApi = converter.toMarks(response)
                    dataSource.deleteAllMarks()
                    marksApi.forEach { dataSource.insertMark(it) }
                    val newMarks = dataSource.getAllMarks().map { converter.toMarkFromEntity(it) }
                    emit(Resource.Success(newMarks))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMarksByCondition(payload: PostMarks): Flow<Resource<List<Mark>>> = flow {
        emit(Resource.Loading())
        val marksLocal = dataSource.getAllMarks().map { converter.toMarkFromEntity(it) }
        emit(Resource.Loading(data = marksLocal))
        try {
            val schoolId = dataSource.getSettings()?.let {
                converter.toSettingsFromEntity(it)
            }?.selectedSchool?.schoolId
            if (schoolId == null) {
                emit(Resource.Error(message = UNKNOWN_ERROR))
            }
            val response = api.postMarks(payload, schoolId ?: "")
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val marksApi = converter.toMarks(response)
                    dataSource.deleteAllMarks()
                    marksApi.forEach { dataSource.insertMark(it) }
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
            val newMarks = dataSource.getAllMarks().map { converter.toMarkFromEntity(it) }
            emit(Resource.Success(newMarks))
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMarksEventItem(infoUrl: String): Flow<Resource<MarksEventItem>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getMarksEvent(infoUrl)
            val markEventItem = converter.toMarkEventItem(response)
            emit(Resource.Success(markEventItem))
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getAttendance(): Flow<Resource<List<Attendance>>> = flow {
        emit(Resource.Loading())
        val attendanceLocal = dataSource.getAllAttendances().map { converter.toAttendanceFromEntity(it) }
        emit(Resource.Loading(data = attendanceLocal))
        try {
            val response = api.getAttendance()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val attendanceApi = converter.toAttendance(response)
                    dataSource.deleteAllAttendance()
                    attendanceApi.forEach { dataSource.insertAttendance(it) }
                    val newAttendance = dataSource.getAllAttendances().map { converter.toAttendanceFromEntity(it) }
                    emit(Resource.Success(newAttendance))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getClassWork(): Flow<Resource<List<ClassWork>>> = flow {
        emit(Resource.Loading())
        val classWorkLocal = dataSource.getAllClassWorks().map { converter.toClassWorkFromEntity(it) }
        emit(Resource.Loading(data = classWorkLocal))
        try {
            val response = api.getClassWork()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val classWorkApi = converter.toClassWork(response)
                    dataSource.deleteAllClassWork()
                    classWorkApi.forEach { dataSource.insertClassWork(it) }
                    val newClassWork = dataSource.getAllClassWorks().map { converter.toClassWorkFromEntity(it) }
                    emit(Resource.Success(newClassWork))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
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
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val classWorkApi = converter.toClassWork(response)
                    dataSource.deleteAllClassWork()
                    classWorkApi.forEach { dataSource.insertClassWork(it) }
                    val newClassWork = dataSource.getAllClassWorks().map { converter.toClassWorkFromEntity(it) }
                    emit(Resource.Success(newClassWork))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getHomeWork(): Flow<Resource<List<HomeWork>>> = flow {
        emit(Resource.Loading())
        val homeWorkLocal = dataSource.getAllHomeWorks().map { converter.toHomeWorkFromEntity(it) }
        emit(Resource.Loading(data = homeWorkLocal))
        try {
            val response = api.getHomeWork()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val homeWorkApi = converter.toHomeWork(response)
                    dataSource.deleteAllHomeWork()
                    homeWorkApi.forEach { dataSource.insertHomeWork(it) }
                    val newHomeWork = dataSource.getAllHomeWorks().map { converter.toHomeWorkFromEntity(it) }
                    emit(Resource.Success(newHomeWork))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
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
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val homeWorkApi = converter.toHomeWork(response)
                    dataSource.deleteAllHomeWork()
                    homeWorkApi.forEach { dataSource.insertHomeWork(it) }
                    val newHomeWork = dataSource.getAllHomeWorks().map { converter.toHomeWorkFromEntity(it) }
                    emit(Resource.Success(newHomeWork))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getControlWork(): Flow<Resource<List<ControlWork>>> = flow {
        emit(Resource.Loading())
        val controlWorkLocal = dataSource.getAllControlWorks().map { converter.toControlWorkFromEntity(it) }
        emit(Resource.Loading(data = controlWorkLocal))
        try {
            val response = api.getControlWork()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val controlWorkApi = converter.toControlWork(response)
                    dataSource.deleteAllControlWork()
                    controlWorkApi.forEach { dataSource.insertControlWork(it) }
                    val newControlWork = dataSource.getAllControlWorks().map { converter.toControlWorkFromEntity(it) }
                    emit(Resource.Success(newControlWork))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
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
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val controlWorkApi = converter.toControlWork(response)
                    dataSource.deleteAllControlWork()
                    controlWorkApi.forEach { dataSource.insertControlWork(it) }
                    val newControlWorks = dataSource.getAllControlWorks().map { converter.toControlWorkFromEntity(it) }
                    emit(Resource.Success(newControlWorks))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getTerm(): Flow<Resource<List<Term>>> = flow {
        emit(Resource.Loading())
        val termsLocal = dataSource.getAllTerms().map { converter.toTermFromEntity(it) }
        emit(Resource.Loading(data = termsLocal))
        try {
            val response = api.getTerm()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val termApi = converter.toTerm(response)
                    dataSource.deleteAllTerm()
                    termApi.forEach { dataSource.insertTerm(it) }
                    val newTerms = dataSource.getAllTerms().map { converter.toTermFromEntity(it) }
                    emit(Resource.Success(newTerms))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getTermMarkDialog(url: String): Flow<Resource<TermMarkDialogItem>> = flow {
        emit(Resource.Loading())
        val termMarkDialogItemLocal = converter.toTermMarkDialogItemFromEntity(dataSource.getTermMarkDialogByUrl(url))
        emit(Resource.Loading(data = termMarkDialogItemLocal))
        try {
            val response = api.getTermMarkDialog(url)
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val termMarkDialogItem = converter.toTermMarkDialogItem(response, url)
                    dataSource.deleteTermMarkDialogByUrl(url)
                    dataSource.insertTermMarkDialog(termMarkDialogItem)
                    val newTermMarkDialog = converter.toTermMarkDialogItemFromEntity(dataSource.getTermMarkDialogByUrl(url))
                    emit(Resource.Success(newTermMarkDialog))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMessagesGotten(): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesGottenLocal = dataSource.getAllMessagesGotten().map { converter.toMessageGottenFromEntity(it) }
        emit(Resource.Loading(data = messagesGottenLocal))
        try {
            val response = api.getMessagesGotten()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val messagesGottenApi = converter.toMessages(response)
                    dataSource.deleteAllMessageGotten()
                    messagesGottenApi.forEach { dataSource.insertMessageGotten(it) }
                    val newMessagesGotten = dataSource.getAllMessagesGotten().map { converter.toMessageGottenFromEntity(it) }
                    emit(Resource.Success(newMessagesGotten))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMessagesGottenByCondition(page: Int): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesGottenLocal = dataSource.getAllMessagesGotten().map { converter.toMessageGottenFromEntity(it) }
        emit(Resource.Loading(data = messagesGottenLocal))
        try {
            val response = api.getMessagesGottenByCondition((page+1).toString())
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val messagesGottenApi = converter.toMessages(response)
                    messagesGottenApi.forEach { dataSource.insertMessageGotten(it) }
                    var newMessagesGotten = dataSource.getAllMessagesGotten().map { converter.toMessageGottenFromEntity(it) }
                    newMessagesGotten = newMessagesGotten.subList(messagesGottenLocal.size, newMessagesGotten.size)
                    emit(Resource.Success(newMessagesGotten))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMessagesSent(): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesSentLocal = dataSource.getAllMessagesSent().map { converter.toMessageSentFromEntity(it) }
        emit(Resource.Loading(data = messagesSentLocal))
        try {
            val response = api.getMessagesSent()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val messagesSentApi = converter.toMessages(response)
                    dataSource.deleteAllMessageSent()
                    messagesSentApi.forEach { dataSource.insertMessageSent(it) }
                    val newMessagesSent = dataSource.getAllMessagesSent().map { converter.toMessageSentFromEntity(it) }
                    emit(Resource.Success(newMessagesSent))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMessagesSentByCondition(page: Int): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesSentLocal = dataSource.getAllMessagesSent().map { converter.toMessageSentFromEntity(it) }
        emit(Resource.Loading(data = messagesSentLocal))
        try {
            val response = api.getMessagesSentByCondition(page.toString())
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val messagesSentApi = converter.toMessages(response)
                    dataSource.deleteAllMessageSent()
                    messagesSentApi.forEach { dataSource.insertMessageSent(it) }
                    var newMessagesSent = dataSource.getAllMessagesSent().map { converter.toMessageSentFromEntity(it) }
                    newMessagesSent = newMessagesSent.subList(messagesSentLocal.size, newMessagesSent.size)
                    emit(Resource.Success(newMessagesSent))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            Log.e("console log", ": load more messages sent")
            e.printStackTrace()
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMessagesStarred(): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesStarredLocal = dataSource.getAllMessagesStarred().map { converter.toMessageStarredFromEntity(it) }
        emit(Resource.Loading(data = messagesStarredLocal))
        try {
            val response = api.getMessagesStarred()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val messagesStarredApi = converter.toMessages(response)
                    dataSource.deleteAllMessageStarred()
                    messagesStarredApi.forEach { dataSource.insertMessageStarred(it) }
                    val newMessagesStarred = dataSource.getAllMessagesStarred().map { converter.toMessageStarredFromEntity(it) }
                    emit(Resource.Success(newMessagesStarred))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMessagesStarredByCondition(page: Int): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesStarredLocal = dataSource.getAllMessagesStarred().map { converter.toMessageStarredFromEntity(it) }
        emit(Resource.Loading(data = messagesStarredLocal))
        try {
            val response = api.getMessagesStarredByCondition(page.toString())
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val messagesStarredApi = converter.toMessages(response)
                    dataSource.deleteAllMessageStarred()
                    messagesStarredApi.forEach { dataSource.insertMessageStarred(it) }
                    var newMessagesStarred = dataSource.getAllMessagesStarred().map { converter.toMessageStarredFromEntity(it) }
                    newMessagesStarred = newMessagesStarred.subList(messagesStarredLocal.size, newMessagesStarred.size)
                    emit(Resource.Success(newMessagesStarred))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMessagesDeleted(): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesDeletedLocal = dataSource.getAllMessagesDeleted().map { converter.toMessageDeletedFromEntity(it) }
        emit(Resource.Loading(data = messagesDeletedLocal))
        try {
            val response = api.getMessagesDeleted()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val messagesDeletedApi = converter.toMessages(response)
                    dataSource.deleteAllMessageDeleted()
                    messagesDeletedApi.forEach { dataSource.insertMessageDeleted(it) }
                    val newMessagesDeleted = dataSource.getAllMessagesDeleted().map { converter.toMessageDeletedFromEntity(it) }
                    emit(Resource.Success(newMessagesDeleted))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMessagesDeletedByCondition(page: Int): Flow<Resource<List<Message>>> = flow {
        emit(Resource.Loading())
        val messagesDeletedLocal = dataSource.getAllMessagesDeleted().map { converter.toMessageDeletedFromEntity(it) }
        emit(Resource.Loading(data = messagesDeletedLocal))
        try {
            val response = api.getMessagesDeletedByCondition(page.toString())
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val messagesDeletedApi = converter.toMessages(response)
                    dataSource.deleteAllMessageDeleted()
                    messagesDeletedApi.forEach { dataSource.insertMessageDeleted(it) }
                    var newMessagesDeleted = dataSource.getAllMessagesDeleted().map { converter.toMessageDeletedFromEntity(it) }
                    newMessagesDeleted = newMessagesDeleted.subList(messagesDeletedLocal.size, newMessagesDeleted.size)
                    emit(Resource.Success(newMessagesDeleted))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMessageIndividual(id: String, isSent: Boolean): Flow<Resource<MessageIndividual>> = flow {
        emit(Resource.Loading())
        dataSource.deleteAllMessageIndividual()
        val messageIndividualLocal = converter.toMessageIndividualFromEntity(dataSource.getMessageIndividualById(id.toLong()))
        emit(Resource.Loading(data = messageIndividualLocal))
        try {
            val response = api.getMessageIndividual(id)
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val messagesIndividualApi = if (!isSent) {
                        converter.toMessagesIndividual(response)
                    } else {
                        converter.toMessagesIndividualSent(response)
                    }
                    dataSource.deleteMessageIndividualById(id.toLong())
                    dataSource.insertMessageIndividual(messagesIndividualApi)
                    val newMessagesIndividual = converter.toMessageIndividualFromEntity(dataSource.getMessageIndividualById(id.toLong()))
                    emit(Resource.Success(newMessagesIndividual))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getHoliday(): Flow<Resource<List<Holiday>>> = flow {
        emit(Resource.Loading())
        val holidayLocal = dataSource.getAllHolidays().map { converter.toHolidayFromEntity(it) }
        emit(Resource.Loading(data = holidayLocal))
        try {
            val response = api.getHoliday()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val holidayApi = converter.toHoliday(response)
                    dataSource.deleteAllHoliday()
                    holidayApi.forEach { dataSource.insertHoliday(it) }
                    val newHoliday = dataSource.getAllHolidays().map { converter.toHolidayFromEntity(it) }
                    emit(Resource.Success(newHoliday))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getParentMeetings(): Flow<Resource<List<ParentMeeting>>> = flow {
        emit(Resource.Loading())
        val parentMeetingsLocal = dataSource.getAllParentMeetings().map { converter.toParentMeetingFromEntity(it) }
        emit(Resource.Loading(data = parentMeetingsLocal))
        try {
            val response = api.getParentMeetings()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val parentMeetingsApi = converter.toParentMeeting(response)
                    dataSource.deleteAllParentMeeting()
                    parentMeetingsApi.forEach { dataSource.insertParentMeeting(it) }
                    val newParentMeetings = dataSource.getAllParentMeetings().map { converter.toParentMeetingFromEntity(it) }
                    emit(Resource.Success(newParentMeetings))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getSchedule(): Flow<Resource<List<ScheduleDay>>> = flow {
        emit(Resource.Loading())
        val scheduleLocal = mutableListOf<ScheduleDay>()
        for (i in 1..7) {
            scheduleLocal.add(ScheduleDay(dataSource.getAllScheduleByWeekDay(i.toLong()).map { converter.toScheduleFromEntity(it) }))
        }
        emit(Resource.Loading(data = scheduleLocal))
        try {
            val response = api.getSchedule()
            when (converter.toPerson(response)) {
                is Resource.Success -> {
                    val scheduleApi = converter.toSchedule(response)
                    dataSource.deleteAllSchedule()
                    scheduleApi.forEach { dataSource.insertSchedule(it) }
                    val newSchedule = mutableListOf<ScheduleDay>()
                    for (i in 1..7) {
                        newSchedule.add(ScheduleDay(dataSource.getAllScheduleByWeekDay(i.toLong()).map { converter.toScheduleFromEntity(it) }))
                    }
                    emit(Resource.Success(newSchedule))
                }
                is Resource.Error -> {
                    emit(Resource.Error(SESSION_COOKIE_EXPIRED))
                }
            }
        } catch (e: RedirectResponseException) {
            emit(Resource.Error(message = SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: HttpRequestTimeoutException){
            emit(Resource.Error(message = TIMEOUT_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }
}