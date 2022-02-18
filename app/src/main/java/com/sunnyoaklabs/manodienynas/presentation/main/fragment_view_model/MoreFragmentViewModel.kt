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
class MoreFragmentViewModel @Inject constructor(
    app: Application,
    private val getHoliday: GetHoliday,
    private val getParentMeetings: GetParentMeetings,
    private val getSchedule: GetSchedule,
    private val getCalendar: GetCalendar,
    private val getCalendarEvent: GetCalendarEvent,
) : AndroidViewModel(app) {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun initHoliday() {
        // TODO(currently not implemented)
    }

    fun initParentMeetings() {
        // TODO(currently not implemented)
    }

    fun initSchedule() {
        // TODO(currently not implemented)
    }

    fun initCalendar() {
        // TODO(currently not implemented)
    }

    fun initCalendarEvent() {
        // TODO(currently not implemented)
    }
}