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
    private val dataSource: DataSource,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : AndroidViewModel(app) {

    private var _keepSignedIn = false

    private var isInitialLogin = false

    private var _errorMessage = ""
    var errorMessage = _errorMessage

    private var _credentials = Credentials("", "")

    private val _userState = MutableStateFlow(UserState(isLoading = true, isUserLoggedIn = false))
    val userState = _userState.asStateFlow()

    private val _isFinishedLoading = MutableSharedFlow<Boolean>()
    val isFinishedLoading = _isFinishedLoading.asSharedFlow()

    init {
        viewModelScope.launch {
            getKeepSignedIn()
            if (!_keepSignedIn && !isInitialLogin) {
                _userState.emit(UserState(
                    isLoading = false,
                    isUserLoggedIn = false
                ))
            }
            getCredentials()
            if (_credentials != Credentials("", "")) {
                if (!isInitialLogin) {
                    /** data will be displayed from cache,
                     *  cookies will be gotten in MainViewModel
                    **/
                    firebaseCrashlytics.log("(MainViewModel) credentials are not null, logging in")
                    _userState.emit(UserState(
                        isLoading = false,
                        isUserLoggedIn = true
                    ))
                } else {
                    /** in initial login situation there will be no cache data loaded
                     *  so cookies request will be initiated in SplashViewModel
                     *  (also verifies if credentials are correct)
                    **/
                    firebaseCrashlytics.log("(MainViewModel) credentials are not null, verifying credentials")
                    validateNetwork()
                    getSessionCookies().collect { wasSessionCreated ->
                        when (wasSessionCreated) {
                            is Resource.Success -> {
                                firebaseCrashlytics.log("Success: credentials, sessionCookies")
                                _userState.emit(UserState(
                                    isLoading = false,
                                    isUserLoggedIn = true
                                ))
                            }
                            is Resource.Error -> {
                                firebaseCrashlytics.log("Error: credentials, sessionCookies")
                                _errorMessage = wasSessionCreated.message ?: UNKNOWN_ERROR
                                _userState.emit(UserState(
                                    isLoading = false,
                                    isUserLoggedIn = false
                                ))
                            }
                            else -> {}
                        }
                    }
                }
            } else {
                firebaseCrashlytics.log("(MainViewModel) credentials are null")
                _errorMessage = NULL_OBJECT_RECEIVED_ERROR
                _userState.emit(UserState(
                    isLoading = false,
                    isUserLoggedIn = false
                ))
            }
        }
    }

    private fun validateNetwork() {
        if (!hasInternetConnection()) {
            viewModelScope.launch {
                if (isInitialLogin) {
                    _errorMessage = IO_ERROR
                    _userState.emit(UserState(
                        isLoading = false,
                        isUserLoggedIn = false
                    ))
                }
                _userState.emit(UserState(
                    isLoading = true,
                    isUserLoggedIn = true
                ))
            }
        }
    }

    private suspend fun getKeepSignedIn() {
        _keepSignedIn = repository.getSettings().keepSignedIn
    }

    private suspend fun getCredentials() {
        _credentials = repository.getCredentials()
    }

    fun setInitialLogin(isInitialLogin: Boolean) {
        this.isInitialLogin = isInitialLogin
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