package com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.data.remote.HttpRoutes
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostMarks
import com.sunnyoaklabs.manodienynas.domain.model.MarksEventItem
import com.sunnyoaklabs.manodienynas.domain.use_case.*
import com.sunnyoaklabs.manodienynas.presentation.main.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class MarksFragmentViewModel @Inject constructor(
    private val getMarks: GetMarks,
    private val getMarksByCondition: GetMarksByCondition,
    private val getMarksEventItem: GetMarksEventItem,
    private val getAttendance: GetAttendance,
    private val getClassWork: GetClassWork,
    private val getClassWorkByCondition: GetClassWorkByCondition,
    private val getHomeWork: GetHomeWork,
    private val getHomeWorkByCondition: GetHomeWorkByCondition,
    private val getControlWork: GetControlWork,
    private val getControlWorkByCondition: GetControlWorkByCondition
) : ViewModel() {

    private val _marksEventItemFlow = MutableSharedFlow<MarksEventItem>()
    val marksEventItemFlow = _marksEventItemFlow.asSharedFlow()

    private val _markTimeRange = mutableStateOf(Pair("", ""))
    val markTimeRange = _markTimeRange
    private val _controlWorkTimeRange = mutableStateOf(Pair("", ""))
    val controlWorkTimeRange = _controlWorkTimeRange
    private val _homeWorkTimeRange = mutableStateOf(Pair("", ""))
    val homeWorkTimeRange = _homeWorkTimeRange
    private val _classWorkTimeRange = mutableStateOf(Pair("", ""))
    val classWorkTimeRange = _classWorkTimeRange

    private val _markFragmentTypeState = mutableStateOf(MarkFragmentTypeState(
        markTypeIsSelected = true,
        controlWorkTypeIsSelected = false,
        homeWorkTypeIsSelected = false,
        classWorkTypeIsSelected = false,
    ))
    val markFragmentTypeState: State<MarkFragmentTypeState> = _markFragmentTypeState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _markState = mutableStateOf(MarkState())
    val markState: State<MarkState> = _markState

    private val _attendanceState = mutableStateOf(AttendanceState())
    val attendanceState: State<AttendanceState> = _attendanceState

    private val _classWorkState = mutableStateOf(ClassWorkState())
    val classWorkState: State<ClassWorkState> = _classWorkState

    private val _homeWorkState = mutableStateOf(HomeWorkState())
    val homeWorkState: State<HomeWorkState> =_homeWorkState

    private val _controlWorkState = mutableStateOf(ControlWorkState())
    val controlWorkState: State<ControlWorkState> = _controlWorkState

    private var getDataJob: Job? = null

    fun onFragmentOpen() {
        getDataJob?.cancel()
        getDataJob = viewModelScope.launch {
            delay(500L)
            if (!_markState.value.isLoading) {
//                initMarks()
            }
        }
    }

    fun initMarks() {
        viewModelScope.launch {
            getMarks().collect {
                when (it) {
                    is Resource.Loading -> {
                        _markState.value = markState.value.copy(
                            marks = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _markState.value = markState.value.copy(
                            marks = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _markState.value = markState.value.copy(
                            marks = it.data ?: emptyList(),
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

    fun initMarksEventItem(infoUrl: String) {
        viewModelScope.launch {
            getMarksEventItem(infoUrl).collect {
                when (it) {
                    is Resource.Loading -> {
                        it.data?.let { item ->
                            _marksEventItemFlow.emit(item)
                        }
                    }
                    is Resource.Success -> {
                        it.data?.let { item ->
                            _marksEventItemFlow.emit(item)
                        }
                    }
                    is Resource.Error -> {
                        it.data?.let { item ->
                            _marksEventItemFlow.emit(item)
                        }
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

    fun initMarksByCondition() {
        viewModelScope.launch {
            getMarksByCondition(getPostMarksPayload()).collect {
                when (it) {
                    is Resource.Loading -> {
                        _markState.value = markState.value.copy(
                            marks = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _markState.value = markState.value.copy(
                            marks = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _markState.value = markState.value.copy(
                            marks = it.data ?: emptyList(),
                            isLoading = true
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

    fun initAttendance() {
        viewModelScope.launch {
            getAttendance().collect {
                when (it) {
                    is Resource.Loading -> {
                        _attendanceState.value = attendanceState.value.copy(
                            attendance = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _attendanceState.value = attendanceState.value.copy(
                            attendance = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _attendanceState.value = attendanceState.value.copy(
                            attendance = it.data ?: emptyList(),
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

    fun initClassWork() {
        viewModelScope.launch {
            getClassWork().collect {
                when (it) {
                    is Resource.Loading -> {
                        _classWorkState.value = classWorkState.value.copy(
                            classWork = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _classWorkState.value = classWorkState.value.copy(
                            classWork = it.data ?: emptyList(),
                            isLoading = false
                        )
                        Log.e("console log", "classWork: "+_classWorkState.value.classWork)
                    }
                    is Resource.Error -> {
                        _classWorkState.value = classWorkState.value.copy(
                            classWork = it.data ?: emptyList(),
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

    fun initClassWorkByCondition() {
        viewModelScope.launch {
            getClassWorkByCondition(getPostClassWorkPayload(), 0).collect {
                when (it) {
                    is Resource.Loading -> {
                        _classWorkState.value = classWorkState.value.copy(
                            classWork = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _classWorkState.value = classWorkState.value.copy(
                            classWork = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _classWorkState.value = classWorkState.value.copy(
                            classWork = it.data ?: emptyList(),
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

    fun initHomeWork() {
        viewModelScope.launch {
            getHomeWork().collect {
                when (it) {
                    is Resource.Loading -> {
                        _homeWorkState.value = homeWorkState.value.copy(
                            homeWork = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _homeWorkState.value = homeWorkState.value.copy(
                            homeWork = it.data ?: emptyList(),
                            isLoading = false
                        )
                        Log.e("console log", "homeWork: "+_homeWorkState.value.homeWork)
                    }
                    is Resource.Error -> {
                        _homeWorkState.value = homeWorkState.value.copy(
                            homeWork = it.data ?: emptyList(),
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

    fun initHomeWorkByCondition() {
        viewModelScope.launch {
            getHomeWorkByCondition(getPostHomeWorkPayload(), 0).collect {
                when (it) {
                    is Resource.Loading -> {
                        _homeWorkState.value = homeWorkState.value.copy(
                            homeWork = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _homeWorkState.value = homeWorkState.value.copy(
                            homeWork = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _homeWorkState.value = homeWorkState.value.copy(
                            homeWork = it.data ?: emptyList(),
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

    fun initControlWork() {
        viewModelScope.launch {
            getControlWork().collect {
                when (it) {
                    is Resource.Loading -> {
                        _controlWorkState.value = controlWorkState.value.copy(
                            controlWork = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _controlWorkState.value = controlWorkState.value.copy(
                            controlWork = it.data ?: emptyList(),
                            isLoading = false
                        )
                        Log.e("console log", "controlWork: "+_controlWorkState.value.controlWork)
                    }
                    is Resource.Error -> {
                        _controlWorkState.value = controlWorkState.value.copy(
                            controlWork = it.data ?: emptyList(),
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

    fun initControlWorkByCondition() {
        viewModelScope.launch {
            getControlWorkByCondition(getPostControlWorkPayload(), 0).collect {
                when (it) {
                    is Resource.Loading -> {
                        _controlWorkState.value = controlWorkState.value.copy(
                            controlWork = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _controlWorkState.value = controlWorkState.value.copy(
                            controlWork = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _controlWorkState.value = controlWorkState.value.copy(
                            controlWork = it.data ?: emptyList(),
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

    fun updateMarkMarkFragmentTypeState() {
        viewModelScope.launch {
            _markFragmentTypeState.value = markFragmentTypeState.value.copy(
                markTypeIsSelected = true,
                controlWorkTypeIsSelected = false,
                homeWorkTypeIsSelected = false,
                classWorkTypeIsSelected = false,
            )
        }
    }

    fun updateControlWorkMarkFragmentTypeState() {
        viewModelScope.launch {
            _markFragmentTypeState.value = markFragmentTypeState.value.copy(
                markTypeIsSelected = false,
                controlWorkTypeIsSelected = true,
                homeWorkTypeIsSelected = false,
                classWorkTypeIsSelected = false,
            )
        }
    }

    fun updateHomeWorkMarkFragmentTypeState() {
        viewModelScope.launch {
            _markFragmentTypeState.value = markFragmentTypeState.value.copy(
                markTypeIsSelected = false,
                controlWorkTypeIsSelected = false,
                homeWorkTypeIsSelected = true,
                classWorkTypeIsSelected = false,
            )
        }
    }

    fun updateClassWorkMarkFragmentTypeState() {
        viewModelScope.launch {
            _markFragmentTypeState.value = markFragmentTypeState.value.copy(
                markTypeIsSelected = false,
                controlWorkTypeIsSelected = false,
                homeWorkTypeIsSelected = false,
                classWorkTypeIsSelected = true,
            )
        }
    }

    fun updateTimeRange(timeRange: Pair<String, String>) {
        when {
            _markFragmentTypeState.value.markTypeIsSelected -> {
                _markTimeRange.value = markTimeRange.value.copy(
                    timeRange.first,
                    timeRange.second
                )
            }
            _markFragmentTypeState.value.controlWorkTypeIsSelected -> {
                _controlWorkTimeRange.value = controlWorkTimeRange.value.copy(
                    timeRange.first,
                    timeRange.second
                )
            }
            _markFragmentTypeState.value.homeWorkTypeIsSelected -> {
                _homeWorkTimeRange.value = homeWorkTimeRange.value.copy(
                    timeRange.first,
                    timeRange.second
                )
            }
            _markFragmentTypeState.value.classWorkTypeIsSelected -> {
                _classWorkTimeRange.value = classWorkTimeRange.value.copy(
                    timeRange.first,
                    timeRange.second
                )
            }
        }
    }

    fun initDataByCondition() {
        viewModelScope.launch {
            when {
                _markFragmentTypeState.value.markTypeIsSelected -> {
                    initMarksByCondition()
                }
                _markFragmentTypeState.value.controlWorkTypeIsSelected -> {
                    initControlWorkByCondition()
                }
                _markFragmentTypeState.value.homeWorkTypeIsSelected -> {
                    initHomeWorkByCondition()
                }
                _markFragmentTypeState.value.classWorkTypeIsSelected -> {
                    initClassWorkByCondition()
                }
            }
        }
    }

    fun formatTimeRange(timeRange: Pair<Long, Long>): Pair<String, String> {
        return Pair(
            formatDateFromMillis(timeRange.first) ?: "",
            formatDateFromMillis(timeRange.second) ?: "",
        )
    }

    private fun formatDateFromMillis(milliseconds : Long?) : String?{
        milliseconds?.let{
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            return formatter.format(calendar.time)
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setInitialTimeRanges() {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val current = LocalDateTime.now()
        _markTimeRange.value = markTimeRange.value.copy(
            current.minusMonths(2).format(dateFormatter),
            current.plusMonths(2).format(dateFormatter)
        )
        _controlWorkTimeRange.value = controlWorkTimeRange.value.copy(
            current.minusMonths(1).format(dateFormatter),
            current.plusMonths(1).format(dateFormatter)
        )
        _homeWorkTimeRange.value = homeWorkTimeRange.value.copy(
            current.minusMonths(1).format(dateFormatter),
            current.plusMonths(1).format(dateFormatter)
        )
        _classWorkTimeRange.value = classWorkTimeRange.value.copy(
            current.minusMonths(1).format(dateFormatter),
            current.plusMonths(1).format(dateFormatter)
        )
    }

    private fun getPostMarksPayload(): PostMarks {
        return PostMarks(
            markTimeRange.value.first,
            markTimeRange.value.second,
            "",
            "/1/lt/page/marks_pupil/marks",
            "",
            ""
        )
    }

    private fun getPostControlWorkPayload(): PostControlWork {
        return PostControlWork(
            _controlWorkTimeRange.value.first,
            _controlWorkTimeRange.value.second,
            "",
            0
        )
    }

    private fun getPostHomeWorkPayload(): PostHomeWork {
        return PostHomeWork(
            _homeWorkTimeRange.value.first,
            _homeWorkTimeRange.value.second,
            "",
            0,
            0
        )
    }

    private fun getPostClassWorkPayload(): PostClassWork {
        return PostClassWork(
            _classWorkTimeRange.value.first,
            _classWorkTimeRange.value.second,
            "",
            0,
            0
        )
    }
}