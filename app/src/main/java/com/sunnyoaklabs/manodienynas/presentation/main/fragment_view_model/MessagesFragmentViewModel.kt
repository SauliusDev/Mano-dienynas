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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessagesFragmentViewModel @Inject constructor(
    private val getMessagesGotten: GetMessagesGotten,
    private val getMessagesSent: GetMessagesSent,
    private val getMessagesStarred: GetMessagesStarred,
    private val getMessagesDeleted: GetMessagesDeleted,
    private val getMessageIndividual: GetMessageIndividual
) : ViewModel() {

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

    private val _messageIndividualState = mutableStateOf(MessageIndividualState())
    val messageIndividualState: State<MessageIndividualState> = _messageIndividualState

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
                        Log.e("console log", "MESSAGE GOTTEN: "+_messagesGottenState.value.messagesGotten)
                    }
                    is Resource.Error -> {
                        Log.e("console log", "MESSAGE GOTTEN: "+it.message)
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
                        Log.e("console log", "MESSAGE SENT: "+_messagesSentState.value.messagesSent)
                    }
                    is Resource.Error -> {
                        Log.e("console log", "MESSAGE SENT: "+it.message)
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
                        Log.e("console log", "MESSAGE STARRED: "+_messagesStarredState.value.messagesStarred)
                    }
                    is Resource.Error -> {
                        Log.e("console log", "MESSAGE STARRED: "+it.message)
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
                        Log.e("console log", "MESSAGE DELETED: "+_messagesDeletedState.value.messagesDeleted)
                    }
                    is Resource.Error -> {
                        Log.e("console log", "MESSAGE DELETED: "+it.message)
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
                        _messageIndividualState.value = messageIndividualState.value.copy(
                            messageIndividual = it.data,
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _messageIndividualState.value = messageIndividualState.value.copy(
                            messageIndividual = it.data,
                            isLoading = false
                        )
                        Log.e("console log", "MESSAGE INDIVIDUAL: "+_messageIndividualState.value.messageIndividual)
                    }
                    is Resource.Error -> {
                        Log.e("console log", "MESSAGE INDIVIDUAL: "+it.message)
                        _messageIndividualState.value = messageIndividualState.value.copy(
                            messageIndividual = it.data,
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