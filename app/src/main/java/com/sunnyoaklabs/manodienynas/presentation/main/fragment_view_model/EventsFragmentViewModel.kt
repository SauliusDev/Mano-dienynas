package com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model

import android.app.Application
import androidx.activity.viewModels
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.domain.use_case.GetEvents
import com.sunnyoaklabs.manodienynas.domain.use_case.GetTerm
import com.sunnyoaklabs.manodienynas.presentation.main.state.EventState
import com.sunnyoaklabs.manodienynas.presentation.main.state.TermState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsFragmentViewModel @Inject constructor(
    app: Application,
    private val getEvents: GetEvents,
    private val getTerm: GetTerm,
) : AndroidViewModel(app) {

    private val _eventState = mutableStateOf(EventState())
    val eventState: State<EventState> = _eventState

    private val _termState = mutableStateOf(TermState())
    val termState: State<TermState> = _termState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun initEvents() {
        viewModelScope.launch {
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
    }

    fun initTerm() {
        viewModelScope.launch {
            getTerm().collect {
                when (it) {
                    is Resource.Loading -> {
                        _termState.value = termState.value.copy(
                            terms = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _termState.value = termState.value.copy(
                            terms = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _termState.value = termState.value.copy(
                            terms = it.data ?: emptyList(),
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