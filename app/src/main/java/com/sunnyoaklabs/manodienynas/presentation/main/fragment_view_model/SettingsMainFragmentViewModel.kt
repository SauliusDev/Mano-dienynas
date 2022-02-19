package com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.remote.BackendApi
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsMainFragmentViewModel @Inject constructor(
    private val repository: Repository,
    private val backendApi: BackendApi,
    private val dataSource: DataSource,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _settingsState = mutableStateOf(Settings())
    val settingsState: State<Settings> = _settingsState

    init {
        getSetting()
    }

    fun logout() {
        viewModelScope.launch {
            backendApi.getLogout()
            deleteEverythingInCache()
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

    fun getSetting() {
        viewModelScope.launch {
            _settingsState.value = repository.getSettings().copy()
        }
    }

    fun deleteSetting() {
        viewModelScope.launch {
            dataSource.deleteSettings()
        }
    }

    fun insertSettings(settings: Settings) {
        viewModelScope.launch {
            dataSource.insertSettings(settings)
        }
    }
}