package com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.core.util.validator.Validator
import com.sunnyoaklabs.manodienynas.domain.use_case.GetEvents
import com.sunnyoaklabs.manodienynas.domain.use_case.GetEventsPage
import com.sunnyoaklabs.manodienynas.presentation.main.state.EventState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventsFragmentViewModel @Inject constructor(
    private val getEvents: GetEvents,
    private val getEventsPage: GetEventsPage,
    val validator: Validator
) : ViewModel() {

    private val _eventState = mutableStateOf(EventState())
    val eventState: State<EventState> = _eventState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                            isLoading = false,
                            isEveryEventLoaded = false
                        )
                    }
                    is Resource.Error -> {
                        _eventState.value = eventState.value.copy(isLoading = false)
                        Log.e("console log 1", ": "+it.message)
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
                        _eventState.value = eventState.value.copy(isLoading = false)
                        Log.e("console log 2", ": "+it.message)
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