package com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostClassWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostControlWork
import com.sunnyoaklabs.manodienynas.data.remote.dto.PostHomeWork
import com.sunnyoaklabs.manodienynas.domain.use_case.*
import com.sunnyoaklabs.manodienynas.presentation.main.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MarksFragmentViewModel @Inject constructor(
    private val getMarks: GetMarks,
    private val getAttendance: GetAttendance,
    private val getClassWork: GetClassWork,
    private val getClassWorkByCondition: GetClassWorkByCondition,
    private val getHomeWork: GetHomeWork,
    private val getHomeWorkByCondition: GetHomeWorkByCondition,
    private val getControlWork: GetControlWork,
    private val getControlWorkByCondition: GetControlWorkByCondition
) : ViewModel() {

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
                        Log.e("console log", "attendance: "+_markState.value.marks)
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
                        Log.e("console log", "attendance: "+_attendanceState.value.attendance)
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

    fun initClassWorkByCondition(payload: PostClassWork, page: Int) {
        viewModelScope.launch {
            getClassWorkByCondition(payload, page).collect {
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

    fun initHomeWorkByCondition(payload: PostHomeWork, page: Int) {
        viewModelScope.launch {
            getHomeWorkByCondition(payload, page).collect {
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

    fun initControlWorkByCondition(payload: PostControlWork, page: Int) {
        viewModelScope.launch {
            getControlWorkByCondition(payload, page).collect {
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
}