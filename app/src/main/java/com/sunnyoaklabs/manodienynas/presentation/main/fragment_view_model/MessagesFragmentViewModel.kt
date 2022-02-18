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
class MessagesFragmentViewModel @Inject constructor(
    app: Application,
    private val getMessagesGotten: GetMessagesGotten,
    private val getMessagesSent: GetMessagesSent,
    private val getMessagesStarred: GetMessagesStarred,
    private val getMessagesDeleted: GetMessagesDeleted,
    private val getMessageIndividual: GetMessageIndividual
) : AndroidViewModel(app) {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun initMessagesGotten() {
        // TODO(currently not implemented)
    }

    fun initMessagesSent() {
        // TODO(currently not implemented)
    }

    fun initMessagesStarred() {
        // TODO(currently not implemented)
    }

    fun initMessagesDeleted() {
        // TODO(currently not implemented)
    }

    fun initMessagesIndividual() {
        // TODO(currently not implemented)
    }
}