package com.sunnyoaklabs.manodienynas.presentation.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sunnyoaklabs.manodienynas.ManoDienynasApp
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetEvents
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSessionCookies
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.*
import com.sunnyoaklabs.manodienynas.presentation.main.state.EventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
    private val repository: Repository,
    private val dataSource: DataSource,
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val getSessionCookies: GetSessionCookies,
    val eventsFragmentViewModel: EventsFragmentViewModel,
    val marksFragmentViewModel: MarksFragmentViewModel,
    val messagesFragmentViewModel: MessagesFragmentViewModel,
    val moreFragmentViewModel: MoreFragmentViewModel,
    val settingsMainFragmentViewModel: SettingsMainFragmentViewModel
) : AndroidViewModel(app) {

    private val _isUserDataGotten = MutableStateFlow(false)
    val isUserDataGotten = _isUserDataGotten.asStateFlow()

    private val _userState = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            // TODO testing
//            this.cancel()
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
                        initData()
                    }
                    is Resource.Error -> {
                        firebaseCrashlytics.log("(MainViewModel) Error: credentials, sessionCookies")
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                wasSessionCreated.message ?: Errors.UNKNOWN_ERROR
                            )
                        )
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

    private fun initData() {
        // TODO figure out if they run async because they need to run ASYNC!!!
        eventsFragmentViewModel.initEvents()
        eventsFragmentViewModel.initTerm()
    }
}