package com.sunnyoaklabs.manodienynas.presentation.main

import android.app.Application
import android.content.Context
import android.content.LocusId
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sunnyoaklabs.manodienynas.ManoDienynasApp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.remote.BackendApi
import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendarDto
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWork
import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.domain.model.Person
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetEvents
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

    private val _userState = mutableStateOf(UserStateState())
    val userState: State<UserStateState> = _userState

    @RequiresApi(Build.VERSION_CODES.O)
    fun initSessionCookies() {
        viewModelScope.launch {
            // TODO testing
            // this.cancel()
            getSessionCookies().collect { wasSessionCreated ->
                when (wasSessionCreated) {
                    is Resource.Success -> {
                        firebaseCrashlytics.log("(MainViewModel) Success: credentials, sessionCookies")
                        _userState.value = userState.value.copy(
                            userState = UserState(
                                isLoading = false,
                                isUserLoggedIn = true,
                                isSessionGotten = true,
                                triedGettingSession = true
                            )
                        )
                        changeRole().join()
                        setInitialValues().join()
                        initData().join()
                    }
                    is Resource.Error -> {
                        firebaseCrashlytics.log("(MainViewModel) Error: credentials, sessionCookies")
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                wasSessionCreated.message ?: Errors.UNKNOWN_ERROR
                            )
                        )
                        _userState.value = userState.value.copy(
                            userState = UserState(
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setInitialValues(): Job {
        return viewModelScope.launch {
            marksFragmentViewModel.setInitialTimeRanges()
        }
    }

    private fun changeRole(): Job {
        return viewModelScope.launch{
            getSettings().collect {
                when(it) {
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

    private fun initData(): Job {
        return CoroutineScope(IO).launch {
            val eventsJob = eventsFragmentViewModel.initEventsAndPerson()
            initPerson(eventsJob)
//            marksFragmentViewModel.initMarksByCondition()
//            marksFragmentViewModel.initAttendance()
//            marksFragmentViewModel.initControlWorkByCondition()
//            marksFragmentViewModel.initClassWorkByCondition()
//            marksFragmentViewModel.initHomeWorkByCondition()
//            messagesFragmentViewModel.initMessagesGotten()
//            messagesFragmentViewModel.initMessagesSent()
//            messagesFragmentViewModel.initMessagesStarred()
//            messagesFragmentViewModel.initMessagesDeleted()
            termsFragmentViewModel.initTerm()
            // all other initializations ..................
        }
    }

    private fun initPerson(eventsJob: Job) {
        viewModelScope.launch {
            // setting loading states
            _personState.value = personState.value.copy(
                person = null,
                isLoading = true
            )
            settingsMainFragmentViewModel.setSettingsStateToLoading()
            // joining coroutines
            eventsJob.join()
            // updating variables
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