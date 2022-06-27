package com.sunnyoaklabs.manodienynas.domain.use_case

import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.domain.model.ClassWork
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetClassWork(
    private val repository: Repository
) {

    operator fun invoke(): Flow<Resource<List<ClassWork>>> {
        return repository.getClassWork()
    }

}