package com.sunnyoaklabs.manodienynas.domain.use_case

import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.domain.model.ScheduleDay
import com.sunnyoaklabs.manodienynas.domain.model.ScheduleOneLesson
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetSchedule(
    private val repository: Repository
) {

    operator fun invoke(): Flow<Resource<List<ScheduleDay>>> {
        return repository.getSchedule()
    }

}