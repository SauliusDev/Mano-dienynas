package com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class MarksFragmentViewModel @Inject constructor(
    app: Application,
    private val getMarks: GetMarks,
    private val getAttendance: GetAttendance,
    private val getClassWork: GetClassWork,
    private val getClassWorkByCondition: GetClassWorkByCondition,
    private val getHomeWork: GetHomeWork,
    private val getHomeWorkByCondition: GetHomeWorkByCondition,
    private val getControlWork: GetControlWork,
    private val getControlWorkByCondition: GetControlWorkByCondition
) : AndroidViewModel(app) {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun initMarks() {
        // TODO(currently not implemented)
    }

    fun initAttendance() {
        // TODO(currently not implemented)
    }

    fun initClassWork() {
        // TODO(currently not implemented)
    }

    fun initClassWorkByCondition() {
        // TODO(currently not implemented)
    }

    fun initHomeWork() {
        // TODO(currently not implemented)
    }

    fun initHomeWorkByCondition() {
        // TODO(currently not implemented)
    }

    fun initControlWork() {
        // TODO(currently not implemented)
    }

    fun initControlWorkByCondition() {
        // TODO(currently not implemented)
    }
}