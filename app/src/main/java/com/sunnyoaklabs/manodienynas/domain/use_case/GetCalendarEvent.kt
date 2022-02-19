package com.sunnyoaklabs.manodienynas.domain.use_case

import android.provider.CalendarContract
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.SessionValidationJsonResponses
import com.sunnyoaklabs.manodienynas.core.util.toDocument
import com.sunnyoaklabs.manodienynas.data.util.Converter
import com.sunnyoaklabs.manodienynas.domain.model.Calendar
import com.sunnyoaklabs.manodienynas.domain.model.CalendarEvent
import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetCalendarEvent(
    private val repository: Repository
) {

    operator fun invoke(id: String): Flow<Resource<CalendarEvent>> {
        return repository.getCalendarEvent(id)
    }

}