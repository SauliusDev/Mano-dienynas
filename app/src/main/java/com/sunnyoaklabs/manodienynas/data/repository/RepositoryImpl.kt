package com.sunnyoaklabs.manodienynas.data.repository

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
        return userSettings.toSettings()
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
        return credentials.toCredentials()
    }

    override fun getEvents(): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val message = api.getEvents()
            val user = converter.toUser(message)
            if (user.name.isNotBlank()) emit(Resource.Success(message))
            else emit(Resource.Error(SESSION_COOKIE_EXPIRED))
        } catch (e: IOException) {
            emit(Resource.Error(message = IO_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(message = UNKNOWN_ERROR))
        }
    }

    override fun getMarks(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getAttendance(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getClassWork(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getClassWorkByCondition(payload: PostClassWork, page: Int): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getHomeWork(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getHomeWorkByCondition(payload: PostHomeWork, page: Int): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getControlWork(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getControlWorkByCondition(payload: PostControlWork, page: Int): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getTerm(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getMessagesGotten(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getMessagesSent(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getMessagesStarred(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getMessagesDeleted(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getMessageIndividual(id: String): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getHoliday(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getParentMeetings(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getSchedule(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getCalendar(): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getCalendarDate(payload: GetCalendar): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun getCalendarEvent(id: String): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }
}