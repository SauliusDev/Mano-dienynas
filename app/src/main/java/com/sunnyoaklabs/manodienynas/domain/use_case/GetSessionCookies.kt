package com.sunnyoaklabs.manodienynas.domain.use_case

import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetSessionCookies(
    private val repository: Repository
) {

    suspend operator fun invoke(): Flow<Resource<String>> {
        return repository.getSessionCookies()
    }
}