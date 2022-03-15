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

class MessagesFragmentViewModel @Inject constructor(
    private val getMessagesGotten: GetMessagesGotten,
    private val getMessagesGottenByCondition: GetMessagesGottenByCondition,
    private val getMessagesSent: GetMessagesSent,
    private val getMessagesSentByCondition: GetMessagesSentByCondition,
    private val getMessagesStarred: GetMessagesStarred,
    private val getMessagesStarredByCondition: GetMessagesStarredByCondition,
    private val getMessagesDeleted: GetMessagesDeleted,
    private val getMessagesDeletedByCondition: GetMessagesDeletedByCondition,
    private val getMessageIndividual: GetMessageIndividual
) : ViewModel() {

    private val _messagesFragmentTypeState = mutableStateOf(MessagesFragmentTypeState())
    val messagesFragmentTypeState: State<MessagesFragmentTypeState> = _messagesFragmentTypeState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _messagesGottenState = mutableStateOf(MessagesGottenState())
    val messagesGottenState: State<MessagesGottenState> = _messagesGottenState

    private val _messagesSentState = mutableStateOf(MessagesSentState())
    val messagesSentState: State<MessagesSentState> = _messagesSentState

    private val _messagesStarredState = mutableStateOf(MessagesStarredState())
    val messagesStarredState: State<MessagesStarredState> = _messagesStarredState

    private val _messagesDeletedState = mutableStateOf(MessagesDeletedState())
    val messagesDeletedState: State<MessagesDeletedState> = _messagesDeletedState

    private val _messageIndividualFlow = MutableSharedFlow<MessageIndividualState>()
    val messageIndividualFlow = _messageIndividualFlow.asSharedFlow()

    private var getDataJob: Job? = null

    fun onFragmentOpen() {
        getDataJob?.cancel()
        getDataJob = viewModelScope.launch {
            delay(500L)

        }
    }

    fun initMessagesGotten() {
        viewModelScope.launch {
            getMessagesGotten().collect {
                when (it) {
                    is Resource.Loading -> {
                        _messagesGottenState.value = messagesGottenState.value.copy(
                            messagesGotten = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _messagesGottenState.value = messagesGottenState.value.copy(
                            messagesGotten = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesGottenState.value = messagesGottenState.value.copy(
                            messagesGotten = it.data ?: emptyList(),
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

    fun initMessagesSent() {
        viewModelScope.launch {
            getMessagesSent().collect {
                when (it) {
                    is Resource.Loading -> {
                        _messagesSentState.value = messagesSentState.value.copy(
                            messagesSent = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _messagesSentState.value = messagesSentState.value.copy(
                            messagesSent = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesSentState.value = messagesSentState.value.copy(
                            messagesSent = it.data ?: emptyList(),
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

    fun initMessagesStarred() {
        viewModelScope.launch {
            getMessagesStarred().collect {
                when (it) {
                    is Resource.Loading -> {
                        _messagesStarredState.value = messagesStarredState.value.copy(
                            messagesStarred = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _messagesStarredState.value = messagesStarredState.value.copy(
                            messagesStarred = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesStarredState.value = messagesStarredState.value.copy(
                            messagesStarred = it.data ?: emptyList(),
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

    fun initMessagesDeleted() {
        viewModelScope.launch {
            getMessagesDeleted().collect {
                when (it) {
                    is Resource.Loading -> {
                        _messagesDeletedState.value = messagesDeletedState.value.copy(
                            messagesDeleted = it.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _messagesDeletedState.value = messagesDeletedState.value.copy(
                            messagesDeleted = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesDeletedState.value = messagesDeletedState.value.copy(
                            messagesDeleted = it.data ?: emptyList(),
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

    fun initMessagesIndividual(id: String) {
        viewModelScope.launch {
            getMessageIndividual(id).collect {
                when (it) {
                    is Resource.Loading -> {
                        // TODO loading progress bar
                    }
                    is Resource.Success -> {
                        Log.e("console log", "2: "+it.data)

                        _messageIndividualFlow.emit(MessageIndividualState(it.data, false))
                    }
                    is Resource.Error -> {
                        _messageIndividualFlow.emit(MessageIndividualState(it.data, false))
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

    fun initMessagesGottenByCondition() {
        viewModelScope.launch {
            getMessagesGottenByCondition(_messagesGottenState.value.page).collect {
                when (it) {
                    is Resource.Loading -> {
                        // TODO show loading
                    }
                    is Resource.Success -> {
                        val newList = _messagesGottenState.value.messagesGotten+(it.data ?: emptyList())
                        _messagesGottenState.value = messagesGottenState.value.copy(
                            messagesGotten = newList,
                            page = _messagesGottenState.value.page+1,
                            isLoading = false
                        )
                        Log.e("console log", "MESSAGE GOTTEN PAGE: "+_messagesGottenState.value.messagesGotten)
                    }
                    is Resource.Error -> {
                        _messagesGottenState.value = messagesGottenState.value.copy(
                            messagesGotten = it.data ?: emptyList(),
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

    fun initMessagesSentByCondition() {
        // TODO
    }

    fun initMessagesStarredByCondition() {
        // TODO
    }

    fun initMessagesDeletedByCondition() {
        // TODO
    }

    fun updateMessagesGottenFragmentTypeState() {
        viewModelScope.launch {
            _messagesFragmentTypeState.value = messagesFragmentTypeState.value.copy(
                gottenIsSelected = true,
                sentIsSelected = false,
                starredIsSelected = false,
                deletedIsSelected = false,
            )
        }
    }

    fun updateMessagesSentFragmentTypeState() {
        viewModelScope.launch {
            _messagesFragmentTypeState.value = messagesFragmentTypeState.value.copy(
                gottenIsSelected = false,
                sentIsSelected = true,
                starredIsSelected = false,
                deletedIsSelected = false,
            )
        }
    }

    fun updateMessagesStarredFragmentTypeState() {
        viewModelScope.launch {
            _messagesFragmentTypeState.value = messagesFragmentTypeState.value.copy(
                gottenIsSelected = false,
                sentIsSelected = false,
                starredIsSelected = true,
                deletedIsSelected = false,
            )
        }
    }

    fun updateMessagesDeletedFragmentTypeState() {
        viewModelScope.launch {
            _messagesFragmentTypeState.value = messagesFragmentTypeState.value.copy(
                gottenIsSelected = false,
                sentIsSelected = false,
                starredIsSelected = false,
                deletedIsSelected = true,
            )
        }
    }

}