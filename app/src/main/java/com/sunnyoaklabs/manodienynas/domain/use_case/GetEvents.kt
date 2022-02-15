package com.sunnyoaklabs.manodienynas.domain.use_case

import android.provider.CalendarContract
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.SessionValidationJsonResponses
import com.sunnyoaklabs.manodienynas.core.util.toDocument
import com.sunnyoaklabs.manodienynas.data.util.Converter
import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetEvents(
    private val repository: Repository,
    private val converter: Converter
) {

    suspend fun invoke(): Flow<Resource<List<Event>>> = flow {
        repository.getEvents().collect {
            when (it) {
                is Resource.Success -> {
                    emit(Resource.Success(converter.toEvents(it.data.toString())))
                }
                is Resource.Error -> {
                    emit(Resource.Error(it.message ?: Errors.UNKNOWN_ERROR))
                }
                else -> {}
            }
        }
    }

}