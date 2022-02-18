package com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.remote.BackendApi
import com.sunnyoaklabs.manodienynas.domain.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsMainFragmentViewModel @Inject constructor(
    app: Application,
    private val backendApi: BackendApi,
    private val dataSource: DataSource,
) : AndroidViewModel(app) {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            dataSource.deleteState()
            dataSource.insertState(State(true))
            deleteEverythingInCache()
            backendApi.getLogout()
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

}