package com.sunnyoaklabs.manodienynas.domain.use_case

import android.provider.CalendarContract
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.SessionValidationJsonResponses
import com.sunnyoaklabs.manodienynas.core.util.toDocument
import com.sunnyoaklabs.manodienynas.data.util.Converter
import com.sunnyoaklabs.manodienynas.domain.model.ClassWork
import com.sunnyoaklabs.manodienynas.domain.model.ControlWork
import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetControlWork(
    private val repository: Repository
) {

    operator fun invoke(): Flow<Resource<List<ControlWork>>> {
        return repository.getControlWork()
    }

}