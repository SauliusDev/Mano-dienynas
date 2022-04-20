package com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.ManoDienynasApp
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.core.util.validator.Validator
import com.sunnyoaklabs.manodienynas.domain.use_case.GetEvents
import com.sunnyoaklabs.manodienynas.domain.use_case.GetEventsPage
import com.sunnyoaklabs.manodienynas.presentation.main.state.EventState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventsFragmentViewModel @Inject constructor(
    private val app: Application,
    private val getEvents: GetEvents,
    private val getEventsPage: GetEventsPage,
    val validator: Validator
) : AndroidViewModel(app) {

    private val _eventState = mutableStateOf(EventState())
    val eventState: State<EventState> = _eventState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun initEventsAndPerson(coroutineScope: CoroutineScope): Job {
        return coroutineScope.launch {
            getEvents().collect {
                when (it) {
                    is Resource.Loading -> {
                        it.data?.let { list ->
                            _eventState.value = eventState.value.copy(
                                events = list,
                                isLoading = true,
                                isLoadingLocale = false,
                                isEveryEventLoaded = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _eventState.value = eventState.value.copy(
                            events = it.data ?: emptyList(),
                            isLoading = false,
                            isEveryEventLoaded = (it.data ?: emptyList()).size % 10 != 0
                        )
                    }
                    is Resource.Error -> {
                        _eventState.value = eventState.value.copy(isLoading = false)
                        _eventFlow.emit(
                            UIEvent.Error(
                                it.message ?: Errors.UNKNOWN_ERROR
                            )
                        )
                    }
                }
            }
        }
    }

    fun loadMoreEvents(coroutineScope: CoroutineScope) {
        if (eventState.value.isEveryEventLoaded) return
        if (_eventState.value.isLoading || !validator.hasInternetConnection(getApplication<ManoDienynasApp>())) return
        coroutineScope.launch {
            getEventsPage().collect {
                when (it) {
                    is Resource.Loading -> {
                        _eventState.value = eventState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val newList = _eventState.value.events + (it.data ?: emptyList())
                        _eventState.value = eventState.value.copy(
                            events = newList,
                            isLoading = false,
                            isEveryEventLoaded = newList.size % 10 != 0
                        )
                    }
                    is Resource.Error -> {
                        _eventState.value = eventState.value.copy(isLoading = false)
                        _eventFlow.emit(
                            UIEvent.Error(
                                it.message ?: Errors.UNKNOWN_ERROR
                            )
                        )
                    }
                }
            }
        }
    }

    fun resetLoadingState() {
        _eventState.value = eventState.value.copy(isLoading = false)
    }
}