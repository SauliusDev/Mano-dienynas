package com.sunnyoaklabs.manodienynas.domain.use_case

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.SessionValidationJsonResponses.CREDENTIALS_CORRECT
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.presentation.main.SplashViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetSessionCookies(
    private val repository: Repository
) {

    suspend operator fun invoke(): Flow<Resource<Boolean>> = flow {
        repository.getSessionCookies().collect {
            when (it) {
                is Resource.Success -> {
                    if (it.data.equals(CREDENTIALS_CORRECT)) emit(Resource.Success(true))
                    else Resource.Error(it.message ?: UNKNOWN_ERROR,false)
                }
                is Resource.Error -> {
                    emit(Resource.Error(it.message ?: UNKNOWN_ERROR,false))
                }
                else -> {}
            }
        }
    }
}