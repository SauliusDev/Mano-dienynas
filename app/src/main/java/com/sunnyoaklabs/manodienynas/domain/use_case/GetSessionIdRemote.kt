package com.sunnyoaklabs.manodienynas.domain.use_case

import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSessionIdRemote(
    private val repository: Repository
) {

    operator fun invoke(credentials: Credentials): Flow<Resource<String>> {
        if (credentials.username.isBlank() || credentials.password.isBlank()) {
            return flow {  }
        }
        return repository.getSessionIdRemote(credentials)
    }
}