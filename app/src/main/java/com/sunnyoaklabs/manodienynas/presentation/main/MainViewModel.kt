package com.sunnyoaklabs.manodienynas.presentation.main

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.squareup.sqldelight.db.migrateWithCallbacks
import com.sunnyoaklabs.manodienynas.MainActivity
import com.sunnyoaklabs.manodienynas.ManoDienynasApp
import com.sunnyoaklabs.manodienynas.ManoDienynasDatabase
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.NULL_OBJECT_RECEIVED_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSessionId
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSessionIdRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: Application,
    private val repository: Repository,
    private val getSessionId: GetSessionId,
    private val getSessionIdRemote: GetSessionIdRemote,
    private val dataSource: DataSource
) : AndroidViewModel(app) {

    private var isInitialLogin = mutableStateOf(false)

    private val _errorMessage = MutableStateFlow("")

    val errorMessage = _errorMessage.asStateFlow()

    val until = MutableStateFlow(false)

    private val _mainScreenState = MutableStateFlow(MainScreenState())
    val mainScreenState = _mainScreenState.asStateFlow()

    var credentials by mutableStateOf(Credentials("", ""))
        private set

//    private var getCredentialsJob: Job

    init {
        viewModelScope.launch {
            delay(1000)
        }
        until.value = false
    }

//    init {
//        _mainScreenState.value = _mainScreenState.value.copy(
//            isLoading = true,
//            isUserLoggedIn = false
//        )
//        getCredentialsJob = viewModelScope.launch {
//            getCredentials()
//        }
//        viewModelScope.launch {
//            getCredentialsJob.join()
//            if (credentials != Credentials("", "")) {
//                FirebaseCrashlytics.getInstance().log("(MainViewModel) credentials are not null")
//                if (!hasInternetConnection()) {
//                    if (isInitialLogin.value) {
//                        _errorMessage.value = IO_ERROR
//                        _mainScreenState.value = _mainScreenState.value.copy(
//                            isLoading = false,
//                            isUserLoggedIn = false
//                        )
//                    }
//                    _mainScreenState.value = _mainScreenState.value.copy(
//                        isLoading = true,
//                        isUserLoggedIn = true
//                    )
//                }
//                getSessionIdRemote(credentials).collect { itSessionId ->
//                    when (itSessionId) {
//                        is Resource.Success -> {
//                            Log.e("console log", ": $itSessionId")
//                            FirebaseCrashlytics.getInstance()
//                                .log("Success: credentials, sessionId")
//                            _mainScreenState.value = _mainScreenState.value.copy(
//                                isLoading = true,
//                                isUserLoggedIn = true
//                            )
//                        }
//                        is Resource.Error -> {
//                            FirebaseCrashlytics.getInstance()
//                                .log("Error: credentials, sessionId")
//                            _errorMessage.value = itSessionId.message ?: UNKNOWN_ERROR
//                            _mainScreenState.value = _mainScreenState.value.copy(
//                                isLoading = false,
//                                isUserLoggedIn = false
//                            )
//                        }
//                        is Resource.Loading -> {
//                            FirebaseCrashlytics.getInstance()
//                                .log("Loading: credentials, sessionId")
//                        }
//                    }
//                }
//            } else {
//                FirebaseCrashlytics.getInstance().log("(MainViewModel) credentials are null")
//                _errorMessage.value = NULL_OBJECT_RECEIVED_ERROR
//                _mainScreenState.value = _mainScreenState.value.copy(
//                    isLoading = false,
//                    isUserLoggedIn = false
//                )
//            }
//        }
//    }

    fun setInitialLogin(isInitialLogin: Boolean) {
        this.isInitialLogin.value = isInitialLogin
    }

    fun getCredentials() {
        viewModelScope.launch { credentials = repository.getCredentials() }
    }

    fun deleteCredentials() {
        viewModelScope.launch { dataSource.deleteCredentials() }
    }

//    private suspend fun safeApiCall() {
//        try {
//            if (hasInternetConnection()) {
//                // todo request
//            } else {
//                Resource.Error("No internet connection")
//            }
//        } catch (t: Throwable) {
//            when(t) {
//                is IOException -> Resource.Error("Network Failure")
//                else -> Resource.Error("Conversion Error")
//            }
//        }
//    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<ManoDienynasApp>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}