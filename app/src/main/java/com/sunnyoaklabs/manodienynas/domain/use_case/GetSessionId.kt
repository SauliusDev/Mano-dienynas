package com.sunnyoaklabs.manodienynas.domain.use_case

import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSessionId(
    private val repository: Repository
) {

    operator fun invoke(): Flow<Resource<String>> {
        return repository.getSessionId()
    }
}