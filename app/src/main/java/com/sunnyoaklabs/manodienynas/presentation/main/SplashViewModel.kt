package com.sunnyoaklabs.manodienynas.presentation.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sunnyoaklabs.manodienynas.ManoDienynasApp
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.NULL_OBJECT_RECEIVED_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSessionCookies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    app: Application,
    private val repository: Repository,
    private val getSessionCookies: GetSessionCookies,
    private val dataSource: DataSource
) : AndroidViewModel(app) {

    private var isInitialLogin = mutableStateOf(false)

    private val _errorMessage = MutableStateFlow("")

    val errorMessage = _errorMessage.asStateFlow()

    private val _userState = MutableStateFlow(UserState(isLoading = true, isUserLoggedIn = false))
    val userState = _userState.asStateFlow()

    private val _credentials = MutableStateFlow(Credentials("", ""))
    val credentials: StateFlow<Credentials> = _credentials

    init {
        viewModelScope.launch {
            getCredentials()
            _credentials.collect {
                Log.e("console log", "cred: $it")
                if (it != Credentials("", "")) {
                    FirebaseCrashlytics.getInstance()
                        .log("(MainViewModel) credentials are not null")
                    validateNetwork()
                    getSessionCookies().collect { wasSessionCreated ->
                        when (wasSessionCreated) {
                            is Resource.Success -> {
                                FirebaseCrashlytics.getInstance()
                                    .log("Success: credentials, sessionCookies")
                                Log.e("console log", ": success")
                                _userState.value = UserState(
                                    isLoading = false,
                                    isUserLoggedIn = true
                                )
                            }
                            is Resource.Error -> {
                                Log.e("console log", ": else else ")
                                FirebaseCrashlytics.getInstance()
                                    .log("Error: credentials, sessionCookies")
                                _errorMessage.value = wasSessionCreated.message ?: UNKNOWN_ERROR
                                _userState.value = UserState(
                                    isLoading = false,
                                    isUserLoggedIn = false
                                )
                            }
                        }
                    }
                } else {
                    FirebaseCrashlytics.getInstance().log("(MainViewModel) credentials are null")
                    _errorMessage.value = NULL_OBJECT_RECEIVED_ERROR
                    _userState.value = UserState(
                        isLoading = false,
                        isUserLoggedIn = false
                    )
                }
            }
        }
    }

    private fun validateNetwork() {
        if (!hasInternetConnection()) {
            if (isInitialLogin.value) {
                _errorMessage.value = IO_ERROR
                _userState.value = UserState(
                    isLoading = false,
                    isUserLoggedIn = false
                )
            }
            _userState.value = UserState(
                isLoading = true,
                isUserLoggedIn = true
            )
        }
    }

    fun setInitialLogin(isInitialLogin: Boolean) {
        this.isInitialLogin.value = isInitialLogin
    }

    private fun getCredentials() {
        viewModelScope.launch { _credentials.value = repository.getCredentials() }
    }

    fun deleteCredentials() {
        viewModelScope.launch { dataSource.deleteCredentials() }
    }

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