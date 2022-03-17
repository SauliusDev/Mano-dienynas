package com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import com.sunnyoaklabs.manodienynas.domain.model.Term
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetEvents
import com.sunnyoaklabs.manodienynas.domain.use_case.GetEventsPage
import com.sunnyoaklabs.manodienynas.domain.use_case.GetTerm
import com.sunnyoaklabs.manodienynas.presentation.main.state.EventState
import com.sunnyoaklabs.manodienynas.presentation.main.state.TermState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EventsFragmentViewModel @Inject constructor(
    private val getEvents: GetEvents,
    private val getEventsPage: GetEventsPage,
) : ViewModel() {

    private val _eventState = mutableStateOf(EventState())
    val eventState: State<EventState> = _eventState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getDataJob: Job? = null

    fun onFragmentOpen() {
        getDataJob?.cancel()
        getDataJob = viewModelScope.launch {
            delay(500L)
            if (!_eventState.value.isLoading){
//                initEventsAndPerson()
            }
        }
    }

    fun initEventsAndPerson(): Job {
        val eventsJob = viewModelScope.launch {
            getEvents().collect {
                when (it) {
                    is Resource.Loading -> {
                        _eventState.value = eventState.value.copy(
                            events = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _eventState.value = eventState.value.copy(
                            events = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _eventState.value = eventState.value.copy(
                            events = it.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                it.message ?: Errors.UNKNOWN_ERROR
                            )
                        )
                    }
                }
            }
        }
        return eventsJob
    }

    fun loadMoreEvents() {
        viewModelScope.launch {
            getEventsPage().collect {
                when (it) {
                    is Resource.Loading -> {
                        // NOTE: could show loading animation
                    }
                    is Resource.Success -> {
                        val newList = _eventState.value.events+(it.data ?: emptyList())
                        _eventState.value = eventState.value.copy(
                            events = newList,
                            isLoading = false,
                            isEveryEventLoaded = newList.size%10 != 0
                        )
                    }
                    is Resource.Error -> {
                        _eventState.value = eventState.value.copy(
                            events = it.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                it.message ?: Errors.UNKNOWN_ERROR
                            )
                        )
                    }
                }
            }
        }
    }
}