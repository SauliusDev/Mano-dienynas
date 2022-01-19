package com.sunnyoaklabs.manodienynas.data.repository

import com.sunnyoaklabs.manodienynas.core.util.toCredentials
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.toSessionId
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.remote.BackendApi
import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendar
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWork
import com.sunnyoaklabs.manodienynas.domain.model.*
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import manodienynas.db.CredentialsEntity
import java.io.IOException

class RepositoryImpl(
    private val api: BackendApi,
    private val dataSource: DataSource
): Repository {

    override fun getSessionId(): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val sessionId = dataSource.getSessionId()

        if (sessionId == null) {
            emit(Resource.Error(
                message = "Empty session id"
            ))
        }

        emit(Resource.Success(sessionId.toSessionId()))
    }

    override fun getCredentials(): Flow<Resource<Credentials>>  = flow {
        emit(Resource.Loading())

        val credentials = dataSource.getCredentials()

        if (credentials == null) {
            emit(Resource.Error(
                message = "Empty credentials"
            ))
        }

        emit(Resource.Success(credentials.toCredentials()))
    }

    override fun getEvents(): Flow<Resource<List<Event>>> = flow {
        TODO("Not yet implemented")
    }

    override fun getMarks(): Flow<Resource<List<Mark>>> {
        TODO("Not yet implemented")
    }

    override fun getAttendance(): Flow<Resource<List<Attendance>>> {
        TODO("Not yet implemented")
    }

    override fun getClassWork(): Flow<Resource<List<ClassWork>>> {
        TODO("Not yet implemented")
    }

    override fun getClassWorkByCondition(payload: PostClassWork): Flow<Resource<List<ClassWork>>> {
        TODO("Not yet implemented")
    }

    override fun getHomeWork(): Flow<Resource<List<HomeWork>>> {
        TODO("Not yet implemented")
    }

    override fun getHomeWorkByCondition(payload: PostHomeWork): Flow<Resource<List<HomeWork>>> {
        TODO("Not yet implemented")
    }

    override fun getControlWork(): Flow<Resource<List<ControlWork>>> {
        TODO("Not yet implemented")
    }

    override fun getControlWorkByCondition(payload: PostControlWork): Flow<Resource<List<ControlWork>>> {
        TODO("Not yet implemented")
    }

    override fun getTerm(): Flow<Resource<List<Term>>> {
        TODO("Not yet implemented")
    }

    override fun getTermLegend(): Flow<Resource<TermLegend>> {
        TODO("Not yet implemented")
    }

    override fun getMessagesGotten(): Flow<Resource<List<Message>>> {
        TODO("Not yet implemented")
    }

    override fun getMessagesSent(): Flow<Resource<List<Message>>> {
        TODO("Not yet implemented")
    }

    override fun getMessagesStarred(): Flow<Resource<List<Message>>> {
        TODO("Not yet implemented")
    }

    override fun getMessagesDeleted(): Flow<Resource<List<Message>>> {
        TODO("Not yet implemented")
    }

    override fun getMessageIndividual(): Flow<Resource<List<MessageIndividual>>> {
        TODO("Not yet implemented")
    }

    override fun getHoliday(): Flow<Resource<List<Holiday>>> {
        TODO("Not yet implemented")
    }

    override fun getParentMeetings(): Flow<Resource<List<ParentMeeting>>> {
        TODO("Not yet implemented")
    }

    override fun getSchedule(): Flow<Resource<List<Schedule>>> {
        TODO("Not yet implemented")
    }

    override fun getCalendar(): Flow<Resource<List<Calendar>>> {
        TODO("Not yet implemented")
    }

    override fun getCalendarDate(payload: GetCalendar): Flow<Resource<List<Calendar>>> {
        TODO("Not yet implemented")
    }

}