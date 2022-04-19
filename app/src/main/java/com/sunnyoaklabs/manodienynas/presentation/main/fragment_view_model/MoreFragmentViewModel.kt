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
import com.sunnyoaklabs.manodienynas.domain.use_case.*
import com.sunnyoaklabs.manodienynas.presentation.main.state.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoreFragmentViewModel @Inject constructor(
    private val getHoliday: GetHoliday,
    private val getParentMeetings: GetParentMeetings,
    private val getSchedule: GetSchedule,
    val validator: Validator
) : ViewModel() {

    private val _moreFragmentTypeState = mutableStateOf(MoreFragmentTypeState())
    val moreFragmentTypeState: State<MoreFragmentTypeState> = _moreFragmentTypeState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _holidayState = mutableStateOf(HolidayState())
    val holidayState: State<HolidayState> = _holidayState

    private val _parentMeetingState = mutableStateOf(ParentMeetingState())
    val parentMeetingState: State<ParentMeetingState> = _parentMeetingState

    private val _scheduleState = mutableStateOf(ScheduleState())
    val scheduleState: State<ScheduleState> = _scheduleState

    fun initHoliday(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            getHoliday().collect {
                when (it) {
                    is Resource.Loading -> {
                        it.data?.let { list ->
                            _holidayState.value = holidayState.value.copy(
                                holiday = list,
                                isLoading = true,
                                isLoadingLocale = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _holidayState.value = holidayState.value.copy(
                            holiday = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _holidayState.value = holidayState.value.copy(isLoading = false,)
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

    fun initParentMeetings(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            getParentMeetings().collect {
                when (it) {
                    is Resource.Loading -> {
                        it.data?.let { list ->
                            _parentMeetingState.value = parentMeetingState.value.copy(
                                parentMeetings = list,
                                isLoading = true,
                                isLoadingLocale = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _parentMeetingState.value = parentMeetingState.value.copy(
                            parentMeetings = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _parentMeetingState.value = parentMeetingState.value.copy(isLoading = false,)
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

    fun initSchedule(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            getSchedule().collect {
                when (it) {
                    is Resource.Loading -> {
                        it.data?.let { list ->
                            _scheduleState.value = scheduleState.value.copy(
                                schedule = list,
                                isLoading = true,
                                isLoadingLocale = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _scheduleState.value = scheduleState.value.copy(
                            schedule = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _scheduleState.value = scheduleState.value.copy(isLoading = false,)
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

    fun updateScheduleMoreFragmentTypeState() {
        viewModelScope.launch {
            _moreFragmentTypeState.value = moreFragmentTypeState.value.copy(
                scheduleIsSelected = true,
                calendarIsSelected = false,
                holidayIsSelected = false,
                parentMeetingsIsSelected = false,
            )
        }
    }

    fun updateCalendarMoreFragmentTypeState() {
        viewModelScope.launch {
            _moreFragmentTypeState.value = moreFragmentTypeState.value.copy(
                scheduleIsSelected = false,
                calendarIsSelected = true,
                holidayIsSelected = false,
                parentMeetingsIsSelected = false,
            )
        }
    }

    fun updateHolidayMoreFragmentTypeState() {
        viewModelScope.launch {
            _moreFragmentTypeState.value = moreFragmentTypeState.value.copy(
                scheduleIsSelected = false,
                calendarIsSelected = false,
                holidayIsSelected = true,
                parentMeetingsIsSelected = false,
            )
        }
    }

    fun updateParentMeetingsMoreFragmentTypeState() {
        viewModelScope.launch {
            _moreFragmentTypeState.value = moreFragmentTypeState.value.copy(
                scheduleIsSelected = false,
                calendarIsSelected = false,
                holidayIsSelected = false,
                parentMeetingsIsSelected = true,
            )
        }
    }

    fun resetLoadingState() {
        _scheduleState.value = scheduleState.value.copy(isLoading = false,)
        _holidayState.value = holidayState.value.copy(isLoading = false,)
        _parentMeetingState.value = parentMeetingState.value.copy(isLoading = false,)
    }

}