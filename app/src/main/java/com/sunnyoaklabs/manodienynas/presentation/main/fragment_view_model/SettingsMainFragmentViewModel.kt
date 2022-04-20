package com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.core.util.EventUITypes.START_ACTIVITY_LOGIN_EVENT_UI_TYPE
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.remote.BackendApi
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSettings
import com.sunnyoaklabs.manodienynas.presentation.main.state.SettingsState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsMainFragmentViewModel @Inject constructor(
    private val repository: Repository,
    private val backendApi: BackendApi,
    private val dataSource: DataSource,
    private val getSettings: GetSettings,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _settingsState = mutableStateOf(SettingsState())
    val settingsState: State<SettingsState> = _settingsState

    init {
        viewModelScope.launch {
            getSetting()
        }
    }

    fun logout() {
        viewModelScope.launch {
            backendApi.getLogout()
            deleteEverythingInCache()
            _eventFlow.emit(UIEvent.ShowToast(START_ACTIVITY_LOGIN_EVENT_UI_TYPE))
        }
    }

    private suspend fun deleteEverythingInCache() {
        dataSource.deleteCredentials()
        dataSource.deleteAllEvents()
        dataSource.deleteAllMarks()
        dataSource.deleteAllAttendance()
        dataSource.deleteAllClassWork()
        dataSource.deleteAllHomeWork()
        dataSource.deleteAllControlWork()
        dataSource.deleteAllTerm()
        dataSource.deleteAllMessageGotten()
        dataSource.deleteAllMessageSent()
        dataSource.deleteAllMessageStarred()
        dataSource.deleteAllMessageDeleted()
        dataSource.deleteAllMessageIndividual()
        dataSource.deleteAllHoliday()
        dataSource.deleteAllParentMeeting()
        dataSource.deleteAllSchedule()
        dataSource.deleteAllCalendar()
        dataSource.deleteAllCalendarEvent()
    }

    fun setSettingsStateToLoading() {
        _settingsState.value = settingsState.value.copy(
            settings = null,
            isLoading = true
        )
    }

     suspend fun getSetting() {
        getSettings().collect {
            when(it) {
                is Resource.Loading -> {
                    _settingsState.value = settingsState.value.copy(
                        settings = null,
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _settingsState.value = settingsState.value.copy(
                        settings = it.data,
                        isLoading = false
                    )
                }
                else -> {}
            }
        }
    }

    private suspend fun deleteSetting() {
        viewModelScope.launch {
            dataSource.deleteSettings()
        }
    }

    private suspend fun insertSettings(settings: Settings) {
        dataSource.insertSettings(settings)
    }

    fun updateSettings(settings: Settings): Job {
        return viewModelScope.launch {
            deleteSetting()
            insertSettings(settings)
            getSetting()
        }
    }
}