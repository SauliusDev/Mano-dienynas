package com.sunnyoaklabs.manodienynas.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sunnyoaklabs.manodienynas.ManoDienynasApp
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSessionId
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSessionIdRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
    repository: Repository,
    private val getSessionId: GetSessionId,
    private val getSessionIdRemote: GetSessionIdRemote
) : AndroidViewModel(app) {

    private val _mainScreenState = MutableStateFlow(MainScreenState())
    val mainScreenState = _mainScreenState.asStateFlow()

    private val _credentials = repository.getCredentials()

    init {
        viewModelScope.launch {
            delay(3000)
            _mainScreenState.value = _mainScreenState.value.copy(
                isLoading = false,
                isUserLoggedIn = false
            )
        }
    }

//    init {
//        viewModelScope.launch {
//            /*
//             things to check:
//             - is login information gotten
//                true -> MainActivity
//                false -> loginActivity
//             */
//            _credentials.collect {
//                when (it) {
//                    is Resource.Success -> {
//                        FirebaseCrashlytics.getInstance().log("Success: credentials")
//                        if (!hasInternetConnection()) {
//                            _mainScreenState.value = _mainScreenState.value.copy(
//                                isLoading = true,
//                                isUserLoggedIn = true
//                            )
//                        }
//                        it.data?.let { credentials ->
//                            getSessionIdRemote(credentials).collect { itSessionId ->
//                                when (itSessionId) {
//                                    is Resource.Success -> {
//                                        FirebaseCrashlytics.getInstance()
//                                            .log("Success: credentials, sessionId")
//                                        _mainScreenState.value = _mainScreenState.value.copy(
//                                            isLoading = true,
//                                            isUserLoggedIn = true
//                                        )
//                                    }
//                                    is Resource.Error -> {
//                                        FirebaseCrashlytics.getInstance()
//                                            .log("Error: credentials, sessionId")
//                                        _errorMessage.value = itSessionId.message ?: UNKNOWN_ERROR
//                                        _mainScreenState.value = _mainScreenState.value.copy(
//                                            isLoading = false,
//                                            isUserLoggedIn = false
//                                        )
//                                    }
//                                    is Resource.Loading -> {
//                                        FirebaseCrashlytics.getInstance()
//                                            .log("Loading: credentials, sessionId")
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    is Resource.Error -> {
//                        FirebaseCrashlytics.getInstance().log("Error: credentials")
//                        _mainScreenState.value = _mainScreenState.value.copy(
//                            isLoading = false,
//                            isUserLoggedIn = false
//                        )
//                    }
//                    is Resource.Loading -> {
//                        FirebaseCrashlytics.getInstance().log("Loading: credentials")
//                    }
//                }
//            }
//        }
//    }

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