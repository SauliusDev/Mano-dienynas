package com.sunnyoaklabs.manodienynas.presentation.splash

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sunnyoaklabs.manodienynas.ManoDienynasApp
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.NULL_OBJECT_RECEIVED_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.validator.Validator
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSessionCookies
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    app: Application,
    private val validator: Validator,
    private val repository: Repository,
    private val getSessionCookies: GetSessionCookies,
    private val dataSource: DataSource,
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val getSettings: GetSettings
) : AndroidViewModel(app) {

    private var _keepSignedIn = false

    private var isInitialLogin = false

    var errorMessage = ""
        private set

    private var _credentials = Credentials("", "")

    private val _userStateSplash = MutableStateFlow(UserStateSplash())
    val userStateSplash = _userStateSplash.asStateFlow()

    fun runSplash() {
        viewModelScope.launch {
            getKeepSignedIn().join()
            getCredentials()
            if (_credentials != Credentials("", "")) {
                if (!isInitialLogin) {
                    /** data will be displayed from cache,
                     *  cookies will be gotten in MainViewModel
                    **/
                    firebaseCrashlytics.log("(MainViewModel) credentials are not null, logging in")
                    _userStateSplash.emit(
                        UserStateSplash(
                        isLoading = false,
                        isUserLoggedIn = true
                    )
                    )
                } else {
                    /** in initial login situation there will be no cache data loaded
                     *  so cookies request will be initiated in SplashViewModel
                     *  (also verifies if credentials are correct)
                    **/
                    firebaseCrashlytics.log("(SplashViewModel) credentials are not null, verifying credentials")
                    if (!!validator.hasInternetConnection(getApplication<ManoDienynasApp>())) {
                        errorMessage = IO_ERROR
                        _userStateSplash.emit(
                            UserStateSplash(
                                isLoading = false,
                                isUserLoggedIn = false
                            )
                        )
                        this.cancel()
                        yield()
                    }
                    getSessionCookies().collect { wasSessionCreated ->
                        when (wasSessionCreated) {
                            is Resource.Success -> {
                                firebaseCrashlytics.log("(SplashViewModel) Success: credentials, sessionCookies")
                                _userStateSplash.emit(
                                    UserStateSplash(
                                    isLoading = false,
                                    isUserLoggedIn = true
                                )
                                )
                            }
                            is Resource.Error -> {
                                firebaseCrashlytics.log("(SplashViewModel) Error: credentials, sessionCookies")
                                errorMessage = wasSessionCreated.message ?: UNKNOWN_ERROR
                                _userStateSplash.emit(
                                    UserStateSplash(
                                    isLoading = false,
                                    isUserLoggedIn = false
                                )
                                )
                            }
                            else -> {}
                        }
                    }
                }
            } else {
                firebaseCrashlytics.log("(MainViewModel) credentials are null")
                errorMessage = NULL_OBJECT_RECEIVED_ERROR
                _userStateSplash.emit(
                    UserStateSplash(
                    isLoading = false,
                    isUserLoggedIn = false
                )
                )
            }
        }
    }

    private suspend fun getKeepSignedIn(): Job {
        return viewModelScope.launch {
            getSettings().collect {
                when(it) {
                    is Resource.Success -> {
                        it.data?.let { settings ->
                            _keepSignedIn = settings.keepSignedIn
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private suspend fun getCredentials() {
        _credentials = repository.getCredentials()
    }

    fun setInitialLogin(isInitialLogin: Boolean) {
        this.isInitialLogin = isInitialLogin
    }
}