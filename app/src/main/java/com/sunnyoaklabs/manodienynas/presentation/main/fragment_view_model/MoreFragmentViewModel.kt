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
import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendarDto
import com.sunnyoaklabs.manodienynas.domain.use_case.*
import com.sunnyoaklabs.manodienynas.presentation.main.state.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoreFragmentViewModel @Inject constructor(
    private val getHoliday: GetHoliday,
    private val getParentMeetings: GetParentMeetings,
    private val getSchedule: GetSchedule,
    //private val getCalendar: GetCalendar,
    //private val getCalendarEvent: GetCalendarEvent,
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

    //private val _calendarState = mutableStateOf(CalendarState())
    //val calendarState: State<CalendarState> = _calendarState

    //private val _calendarEventState = mutableStateOf(CalendarEventState())
    //val calendarEventState: State<CalendarEventState> = _calendarEventState

    private var getDataJob: Job? = null

    fun onFragmentOpen() {
        getDataJob?.cancel()
        getDataJob = viewModelScope.launch {
            delay(500L)
            if (!_scheduleState.value.isLoading ||
                !_holidayState.value.isLoading ||
                !_parentMeetingState.value.isLoading) {
                initSchedule()
                initHoliday()
                initParentMeetings()
            }
        }
    }

    fun initHoliday() {
        viewModelScope.launch {
            getHoliday().collect {
                when (it) {
                    is Resource.Loading -> {
                        _holidayState.value = holidayState.value.copy(
                            holiday = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _holidayState.value = holidayState.value.copy(
                            holiday = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _holidayState.value = holidayState.value.copy(
                            holiday = it.data ?: emptyList(),
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

    fun initParentMeetings() {
        viewModelScope.launch {
            getParentMeetings().collect {
                when (it) {
                    is Resource.Loading -> {
                        _parentMeetingState.value = parentMeetingState.value.copy(
                            parentMeetings = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _parentMeetingState.value = parentMeetingState.value.copy(
                            parentMeetings = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _parentMeetingState.value = parentMeetingState.value.copy(
                            parentMeetings = it.data ?: emptyList(),
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

    fun initSchedule() {
        viewModelScope.launch {
            getSchedule().collect {
                when (it) {
                    is Resource.Loading -> {
                        _scheduleState.value = scheduleState.value.copy(
                            schedule = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _scheduleState.value = scheduleState.value.copy(
                            schedule = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _scheduleState.value = scheduleState.value.copy(
                            schedule = it.data ?: emptyList(),
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

    //    fun initCalendar(payload: GetCalendarDto) {
//        viewModelScope.launch {
//            getCalendar(payload).collect {
//                when (it) {
//                    is Resource.Loading -> {
//                        _calendarState.value = calendarState.value.copy(
//                            calendar = it.data ?: emptyList(),
//                            isLoading = true
//                        )
//                    }
//                    is Resource.Success -> {
//                        _calendarState.value = calendarState.value.copy(
//                            calendar = it.data ?: emptyList(),
//                            isLoading = false
//                        )
//                        Log.e("console log", "holiday: "+_calendarState.value.calendar)
//                    }
//                    is Resource.Error -> {
//                        Log.e("console log", "calendar: "+it.message)
//                        _calendarState.value = calendarState.value.copy(
//                            calendar = it.data ?: emptyList(),
//                            isLoading = false
//                        )
//                        _eventFlow.emit(
//                            UIEvent.ShowSnackbar(
//                                it.message ?: Errors.UNKNOWN_ERROR
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    }

//    fun initCalendarEvent(id: String) {
//        viewModelScope.launch {
//            getCalendarEvent(id).collect {
//                when (it) {
//                    is Resource.Loading -> {
//                        _calendarEventState.value = calendarEventState.value.copy(
//                            calendar = it.data,
//                            isLoading = true
//                        )
//                    }
//                    is Resource.Success -> {
//                        _calendarEventState.value = calendarEventState.value.copy(
//                            calendar = it.data,
//                            isLoading = false
//                        )
//                        Log.e("console log", "calendar event: "+_calendarEventState.value.calendar)
//                    }
//                    is Resource.Error -> {
//                        Log.e("console log", "calendar event: "+it.message)
//                        _calendarEventState.value = calendarEventState.value.copy(
//                            calendar = it.data,
//                            isLoading = false
//                        )
//                        _eventFlow.emit(
//                            UIEvent.ShowSnackbar(
//                                it.message ?: Errors.UNKNOWN_ERROR
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    }

}