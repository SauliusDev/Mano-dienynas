package com.sunnyoaklabs.manodienynas.presentation.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sunnyoaklabs.manodienynas.ManoDienynasApp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Fragments.EVENTS_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MARKS_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MESSAGES_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MORE_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.SETTINGS_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.TERMS_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.remote.BackendApi
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSessionCookies
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSettings
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.*
import com.sunnyoaklabs.manodienynas.presentation.main.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import java.io.BufferedReader
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: Application,
    private val repository: Repository,
    private val dataSource: DataSource,
    private val backendApi: BackendApi,
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val getSessionCookies: GetSessionCookies,
    private val getSettings: GetSettings,
    val eventsFragmentViewModel: EventsFragmentViewModel,
    val marksFragmentViewModel: MarksFragmentViewModel,
    val messagesFragmentViewModel: MessagesFragmentViewModel,
    val termsFragmentViewModel: TermsFragmentViewModel,
    val moreFragmentViewModel: MoreFragmentViewModel,
    val settingsMainFragmentViewModel: SettingsMainFragmentViewModel
) : AndroidViewModel(app) {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _personState = mutableStateOf(PersonState())
    val personState: State<PersonState> = _personState

    private val _userStateState = mutableStateOf(UserStateState())
    val userStateState: State<UserStateState> = _userStateState

    private var latestDataLoadOrigin = ""

    var getDataJob: Job? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun onFragmentOpen(fragment: String) {
        verifySessionCookies()
        getDataJob?.cancel()
        getDataJob = viewModelScope.launch {
            resetLoadingState()
            latestDataLoadOrigin = fragment
            when (fragment) {
                EVENTS_FRAGMENT -> {
                    Log.e("console log", ": initas eventu ")
                    eventsFragmentViewModel.initEventsAndPerson()
                }
                MARKS_FRAGMENT -> {
                    marksFragmentViewModel.initMarksByCondition()
                    marksFragmentViewModel.initAttendance()
                    marksFragmentViewModel.initControlWorkByCondition()
                    marksFragmentViewModel.initClassWorkByCondition()
                    marksFragmentViewModel.initHomeWorkByCondition()
                }
                MESSAGES_FRAGMENT -> {
                    messagesFragmentViewModel.initMessagesGotten()
                    messagesFragmentViewModel.initMessagesSent()
                    messagesFragmentViewModel.initMessagesStarred()
                    messagesFragmentViewModel.initMessagesDeleted()
                }
                TERMS_FRAGMENT -> {
                    termsFragmentViewModel.initTerm()
                }
                MORE_FRAGMENT -> {
                    moreFragmentViewModel.initSchedule()
                    moreFragmentViewModel.initHoliday()
                    moreFragmentViewModel.initParentMeetings()
                }
                SETTINGS_FRAGMENT -> {
                    // NOTE: no data to load ;(
                }
            }
        }
    }

    private fun resetLoadingState() {
        when (latestDataLoadOrigin) {
            EVENTS_FRAGMENT -> {
                eventsFragmentViewModel.resetLoadingState()
            }
            MARKS_FRAGMENT -> {
                marksFragmentViewModel.resetLoadingState()
            }
            MESSAGES_FRAGMENT -> {
                messagesFragmentViewModel.resetLoadingState()
            }
            TERMS_FRAGMENT -> {
                termsFragmentViewModel.resetLoadingState()
            }
            MORE_FRAGMENT -> {
                moreFragmentViewModel.resetLoadingState()
            }
            SETTINGS_FRAGMENT -> {
                // NOTE: no data state to reset ;(
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun verifySessionCookies() {
        viewModelScope.launch {
            if (!_userStateState.value.isLoading && !_userStateState.value.userState?.isSessionGotten!!) {
                initSessionCookies().join()
                if (_userStateState.value.userState?.isSessionGotten!!) {
                    onFragmentOpen(latestDataLoadOrigin)
                }
            }
        }
    }

    fun initSessionCookies(): Job {
        return viewModelScope.launch {
            getSessionCookies().collect { wasSessionCreated ->
                when (wasSessionCreated) {
                    is Resource.Loading -> {
                        firebaseCrashlytics.log("(MainViewModel) Loading: credentials, sessionCookies")
                        _userStateState.value = userStateState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        firebaseCrashlytics.log("(MainViewModel) Success: credentials, sessionCookies")
                        _userStateState.value = userStateState.value.copy(
                            userState = UserState(
                                isUserLoggedIn = true,
                                isSessionGotten = true,
                                triedGettingSession = true
                            ),
                            isLoading = false
                        )
                        changeRole().join()
                        setInitialValues().join()
                        initMainData().join()
                    }
                    is Resource.Error -> {
                        onFragmentOpen(EVENTS_FRAGMENT) // load first screen data from cache
                        firebaseCrashlytics.log("(MainViewModel) Error: credentials, sessionCookies")
                        _eventFlow.emit(
                            UIEvent.ShowToast(
                                wasSessionCreated.message ?: Errors.UNKNOWN_ERROR
                            )
                        )
                        _userStateState.value = userStateState.value.copy(
                            userState = UserState(
                                isUserLoggedIn = true,
                                isSessionGotten = false,
                                triedGettingSession = true
                            ),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun setInitialValues(): Job {
        return viewModelScope.launch {
            marksFragmentViewModel.setInitialTimeRanges()
        }
    }

    private fun changeRole(): Job {
        return viewModelScope.launch {
            getSettings().collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.selectedSchool?.let { selectedSchool ->
                            backendApi.getChangeRole(selectedSchool.schoolId)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initMainData(): Job {
        return viewModelScope.launch {
            val eventsJob = eventsFragmentViewModel.initEventsAndPerson()
            initPerson(eventsJob)
        }
    }

    private fun initPerson(eventsJob: Job) {
        viewModelScope.launch {
            _personState.value = personState.value.copy(
                person = null,
                isLoading = true
            )
            settingsMainFragmentViewModel.setSettingsStateToLoading()
            eventsJob.join()
            viewModelScope.launch {
                settingsMainFragmentViewModel.getSetting()
            }
            getPerson()
        }
    }

    private suspend fun getPerson() {
        _personState.value = personState.value.copy(
            person = repository.getPerson(),
            isLoading = false
        )
    }

    fun getAppDescription(): String {
        return app.resources.openRawResource(R.raw.app_description)
            .bufferedReader().use(BufferedReader::readText)
    }

    fun getAppLicense(): String {
        return app.resources.openRawResource(R.raw.app_license)
            .bufferedReader().use(BufferedReader::readText)
    }
}