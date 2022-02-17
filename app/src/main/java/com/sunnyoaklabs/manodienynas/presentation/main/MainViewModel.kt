package com.sunnyoaklabs.manodienynas.presentation.main

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
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSessionCookies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: Application,
    private val repository: Repository,
    private val dataSource: DataSource,
    private val getSessionCookies: GetSessionCookies,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : AndroidViewModel(app) {

    private val _isUserDataGotten = MutableStateFlow(false)
    val isUserDataGotten = _isUserDataGotten.asStateFlow()

    private val _userState = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()

    private val _notifier = MutableSharedFlow<Resource<String>>()
    val notifier = _notifier.asSharedFlow()

    init {
        viewModelScope.launch {
            // TODO testing
            this.cancel()

            getSessionCookies().collect { wasSessionCreated ->
                when (wasSessionCreated) {
                    is Resource.Success -> {
                        firebaseCrashlytics.log("(MainViewModel) Success: credentials, sessionCookies")
                        _userState.emit(
                            UserState(
                                isLoading = false,
                                isUserLoggedIn = true,
                                isSessionGotten = true,
                                triedGettingSession = true
                            )
                        )
                    }
                    is Resource.Error -> {
                        firebaseCrashlytics.log("(MainViewModel) Error: credentials, sessionCookies")
                        _notifier.emit(Resource.Error(wasSessionCreated.message ?: Errors.UNKNOWN_ERROR))
                        _userState.emit(
                            UserState(
                                isLoading = false,
                                isUserLoggedIn = true,
                                isSessionGotten = false,
                                triedGettingSession = true
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
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