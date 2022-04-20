package com.sunnyoaklabs.manodienynas.presentation.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sunnyoaklabs.manodienynas.ManoDienynasApp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.SESSION_COOKIE_EXPIRED
import com.sunnyoaklabs.manodienynas.core.util.Fragments.EVENTS_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MARKS_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MARKS_FRAGMENT_CLASS_WORK
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MARKS_FRAGMENT_CONTROL_WORK
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MARKS_FRAGMENT_HOME_WORK
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MARKS_FRAGMENT_MARKS
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MESSAGES_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MESSAGES_FRAGMENT_DELETED
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MESSAGES_FRAGMENT_GOTTEN
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MESSAGES_FRAGMENT_SENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MESSAGES_FRAGMENT_STARRED
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MORE_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MORE_FRAGMENT_HOLIDAY
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MORE_FRAGMENT_MEETINGS
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MORE_FRAGMENT_SCHEDULE
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
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

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

    private var latestDataLoadOrigin = EVENTS_FRAGMENT

    private var dataLoadScope: CoroutineScope = CoroutineScope(Job())

    @RequiresApi(Build.VERSION_CODES.O)
    fun onFragmentOpen(fragment: String) {
        verifySessionCookies()
        dataLoadScope.cancel()
        dataLoadScope = CoroutineScope(Job())
        resetLoadingState()
        latestDataLoadOrigin = fragment
        when (fragment) {
            EVENTS_FRAGMENT -> {
                eventsFragmentViewModel.initEventsAndPerson(dataLoadScope)
            }
            MARKS_FRAGMENT -> {
                marksFragmentViewModel.initMarksByCondition(dataLoadScope)
                marksFragmentViewModel.initAttendance(dataLoadScope)
                marksFragmentViewModel.initControlWorkByCondition(dataLoadScope)
                marksFragmentViewModel.initClassWork(dataLoadScope)
                marksFragmentViewModel.initHomeWork(dataLoadScope)
            }
            MESSAGES_FRAGMENT -> {
                messagesFragmentViewModel.initMessagesGotten(dataLoadScope)
                messagesFragmentViewModel.initMessagesSent(dataLoadScope)
                messagesFragmentViewModel.initMessagesStarred(dataLoadScope)
                messagesFragmentViewModel.initMessagesDeleted(dataLoadScope)
            }
            TERMS_FRAGMENT -> {
                termsFragmentViewModel.initTerm(dataLoadScope)
            }
            MORE_FRAGMENT -> {
                moreFragmentViewModel.initSchedule(dataLoadScope)
                moreFragmentViewModel.initHoliday(dataLoadScope)
                moreFragmentViewModel.initParentMeetings(dataLoadScope)
            }
        }
    }

    init {
        viewModelScope.launch {
            async {
                eventsFragmentViewModel.eventFlow.collectLatest {
                    processEvent(it)
                }
            }
            async {
                marksFragmentViewModel.eventFlow.collectLatest {
                    processEvent(it)
                }
            }
            async {
                messagesFragmentViewModel.eventFlow.collectLatest {
                    processEvent(it)
                }
            }
            async {
                termsFragmentViewModel.eventFlow.collectLatest {
                    processEvent(it)
                }
            }
            async {
                moreFragmentViewModel.eventFlow.collectLatest {
                    processEvent(it)
                }
            }
            async {
                settingsMainFragmentViewModel.eventFlow.collectLatest {
                    processEvent(it)
                }
            }
        }
    }

    private suspend fun processEvent(
        uiEvent: UIEvent,
    ) {
        when (uiEvent) {
            is UIEvent.ShowToast, is UIEvent.StartActivity -> {
                _eventFlow.emit(uiEvent)
            }
            is UIEvent.Error -> {
                when (uiEvent.message) {
                    SESSION_COOKIE_EXPIRED -> {
                        if (!_userStateState.value.isLoading) {
                            initSessionCookies()
                        }
                    }
                    IO_ERROR -> {
                        // Showing snackbar on every onFragmentOpen() would be too annoying for the user
                    }
                    else -> {
                        _eventFlow.emit(UIEvent.ShowToast(uiEvent.message))
                    }
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
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun verifySessionCookies() {
        _userStateState.value.userState?.let {
            if (!_userStateState.value.isLoading && !_userStateState.value.userState?.isSessionGotten!!) {
                initSessionCookies()
            }
        }
    }

    fun initSessionCookies(): Job {
        _userStateState.value.userState ?: run {
            onFragmentOpen(latestDataLoadOrigin) // load  first screen data from cache
        }
        return viewModelScope.launch {
            getSessionCookies().collect {
                when (it) {
                    is Resource.Loading -> {
                        firebaseCrashlytics.log("(MainViewModel) Loading: credentials, sessionCookies")
                        _userStateState.value = userStateState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        firebaseCrashlytics.log("(MainViewModel) Success: credentials, sessionCookies")
                        changeRole().join()
                        setInitialValues().join()
                        initMainData().join()
                        _userStateState.value = userStateState.value.copy(
                            userState = UserState(
                                isUserLoggedIn = true,
                                isSessionGotten = true,
                            ),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        firebaseCrashlytics.log("(MainViewModel) Error: credentials, sessionCookies")
                        _eventFlow.emit(
                            UIEvent.Error(
                                it.message ?: Errors.UNKNOWN_ERROR
                            )
                        )
                        _userStateState.value = userStateState.value.copy(
                            userState = UserState(
                                isUserLoggedIn = true,
                                isSessionGotten = false,
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
            eventsFragmentViewModel.initEventsAndPerson(this).join()
            initPerson()
            // after cookies have loaded, currently selected fragment data needs to be synced
            if (latestDataLoadOrigin != EVENTS_FRAGMENT) onFragmentOpen(latestDataLoadOrigin)
        }
    }

    private fun initPerson() {
        viewModelScope.launch {
            _personState.value = personState.value.copy(
                person = null,
                isLoading = true
            )
            settingsMainFragmentViewModel.setSettingsStateToLoading()
            launch { settingsMainFragmentViewModel.getSetting() }
            getPerson()
        }
    }

    // data loading function called from UI
    fun initDataOnCalendarSelect() {
        when {
            marksFragmentViewModel.markFragmentTypeState.value.markTypeIsSelected -> {
                marksFragmentViewModel.initMarksByCondition(dataLoadScope)
            }
            marksFragmentViewModel.markFragmentTypeState.value.controlWorkTypeIsSelected -> {
                marksFragmentViewModel.initControlWorkByCondition(dataLoadScope)
            }
            marksFragmentViewModel.markFragmentTypeState.value.homeWorkTypeIsSelected -> {
                marksFragmentViewModel.initHomeWork(dataLoadScope)
            }
            marksFragmentViewModel.markFragmentTypeState.value.classWorkTypeIsSelected -> {
                marksFragmentViewModel.initClassWork(dataLoadScope)
            }
        }
    }
    fun initDataOnEmptyFragment(fragment: String, specificFragmentDataOriginType: String) {
        when (fragment) {
            EVENTS_FRAGMENT -> {
                eventsFragmentViewModel.initEventsAndPerson(dataLoadScope)
            }
            MARKS_FRAGMENT -> {
                when (specificFragmentDataOriginType) {
                    MARKS_FRAGMENT_MARKS -> {
                        marksFragmentViewModel.initMarksByCondition(dataLoadScope)
                        marksFragmentViewModel.initAttendance(dataLoadScope)
                    }
                    MARKS_FRAGMENT_CONTROL_WORK -> {
                        marksFragmentViewModel.initControlWorkByCondition(dataLoadScope)
                    }
                    MARKS_FRAGMENT_HOME_WORK -> {
                        marksFragmentViewModel.initHomeWork(dataLoadScope)
                    }
                    MARKS_FRAGMENT_CLASS_WORK -> {
                        marksFragmentViewModel.initClassWork(dataLoadScope)
                    }
                }
            }
            MESSAGES_FRAGMENT -> {
                when (specificFragmentDataOriginType) {
                    MESSAGES_FRAGMENT_GOTTEN -> {
                        messagesFragmentViewModel.initMessagesGotten(dataLoadScope)
                    }
                    MESSAGES_FRAGMENT_SENT -> {
                        messagesFragmentViewModel.initMessagesSent(dataLoadScope)
                    }
                    MESSAGES_FRAGMENT_STARRED -> {
                        messagesFragmentViewModel.initMessagesStarred(dataLoadScope)
                    }
                    MESSAGES_FRAGMENT_DELETED -> {
                        messagesFragmentViewModel.initMessagesDeleted(dataLoadScope)
                    }
                }
            }
            TERMS_FRAGMENT -> {
                termsFragmentViewModel.initTerm(dataLoadScope)
            }
            MORE_FRAGMENT -> {
                when (specificFragmentDataOriginType) {
                    MORE_FRAGMENT_SCHEDULE -> {
                        moreFragmentViewModel.initSchedule(dataLoadScope)
                    }
                    MORE_FRAGMENT_HOLIDAY -> {
                        moreFragmentViewModel.initHoliday(dataLoadScope)
                    }
                    MORE_FRAGMENT_MEETINGS -> {
                        moreFragmentViewModel.initParentMeetings(dataLoadScope)
                    }
                }
            }
        }
    }
    fun initPagingDataFromFragment(fragment: String, specificFragmentDataOriginType: String) {
        when (fragment) {
            EVENTS_FRAGMENT -> {
                eventsFragmentViewModel.loadMoreEvents(dataLoadScope)
            }
            MARKS_FRAGMENT -> {
                when (specificFragmentDataOriginType) {
                    MARKS_FRAGMENT_HOME_WORK -> {
                        marksFragmentViewModel.initHomeWorkByCondition(
                            marksFragmentViewModel.homeWorkState.value.page + 1,
                            dataLoadScope
                        )
                    }
                    MARKS_FRAGMENT_CLASS_WORK -> {
                        marksFragmentViewModel.initClassWorkByCondition(
                            marksFragmentViewModel.classWorkState.value.page + 1,
                            dataLoadScope
                        )
                    }
                }
            }
            MESSAGES_FRAGMENT -> {
                when (specificFragmentDataOriginType) {
                    MESSAGES_FRAGMENT_GOTTEN -> {
                        messagesFragmentViewModel.initMessagesGottenByCondition(dataLoadScope)
                    }
                    MESSAGES_FRAGMENT_SENT -> {
                        messagesFragmentViewModel.initMessagesSentByCondition(dataLoadScope)
                    }
                    MESSAGES_FRAGMENT_STARRED -> {
                        messagesFragmentViewModel.initMessagesStarredByCondition(dataLoadScope)

                    }
                    MESSAGES_FRAGMENT_DELETED -> {
                        messagesFragmentViewModel.initMessagesDeletedByCondition(dataLoadScope)
                    }
                }
            }
        }
    }
    fun initExtraItemDataFromFragment(fragment: String, specificFragmentDataOriginType: String, data: Any? = null) {
        when (fragment) {
            MARKS_FRAGMENT -> {
                when (specificFragmentDataOriginType) {
                    MARKS_FRAGMENT_MARKS -> {
                        marksFragmentViewModel.initMarksEventItem((data as String), dataLoadScope)
                    }
                }
            }
            MESSAGES_FRAGMENT -> {
                when (specificFragmentDataOriginType) {
                    MESSAGES_FRAGMENT_GOTTEN -> {
                        messagesFragmentViewModel.initMessagesIndividual((data as String), dataLoadScope)
                    }
                    MESSAGES_FRAGMENT_SENT -> {
                        messagesFragmentViewModel.initMessagesIndividual((data as String), dataLoadScope, true)
                    }
                    MESSAGES_FRAGMENT_STARRED -> {
                        messagesFragmentViewModel.initMessagesIndividual((data as String), dataLoadScope)
                    }
                    MESSAGES_FRAGMENT_DELETED -> {
                        messagesFragmentViewModel.initMessagesIndividual((data as String), dataLoadScope)
                    }
                }
            }
            TERMS_FRAGMENT -> {
                termsFragmentViewModel.initTermMarkDialogItem((data as String), dataLoadScope)
            }
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