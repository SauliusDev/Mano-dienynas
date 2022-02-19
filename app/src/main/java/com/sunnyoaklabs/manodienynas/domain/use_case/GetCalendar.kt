package com.sunnyoaklabs.manodienynas.domain.use_case

import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendarDto
import com.sunnyoaklabs.manodienynas.domain.model.Calendar
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetCalendar(
    private val repository: Repository
) {

    operator fun invoke(payload: GetCalendarDto): Flow<Resource<List<Calendar>>> {
        return repository.getCalendar(payload)
    }

}