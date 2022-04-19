package com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.ManoDienynasApp
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

class MessagesFragmentViewModel @Inject constructor(
    private val app: Application,
    private val getMessagesGotten: GetMessagesGotten,
    private val getMessagesGottenByCondition: GetMessagesGottenByCondition,
    private val getMessagesSent: GetMessagesSent,
    private val getMessagesSentByCondition: GetMessagesSentByCondition,
    private val getMessagesStarred: GetMessagesStarred,
    private val getMessagesStarredByCondition: GetMessagesStarredByCondition,
    private val getMessagesDeleted: GetMessagesDeleted,
    private val getMessagesDeletedByCondition: GetMessagesDeletedByCondition,
    private val getMessageIndividual: GetMessageIndividual,
    val validator: Validator
) : AndroidViewModel(app) {

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

    fun initMessagesGotten(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            getMessagesGotten().collect {
                when (it) {
                    is Resource.Loading -> {
                        it.data?.let { list ->
                            _messagesGottenState.value = messagesGottenState.value.copy(
                                messagesGotten = list,
                                isLoading = true,
                                isLoadingLocale = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _messagesGottenState.value = messagesGottenState.value.copy(
                            messagesGotten = it.data ?: emptyList(),
                            page = 1,
                            isEverythingLoaded = false,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesGottenState.value = messagesGottenState.value.copy(isLoading = false,)
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

    fun initMessagesSent(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            getMessagesSent().collect {
                when (it) {
                    is Resource.Loading -> {
                        it.data?.let { list ->
                            _messagesSentState.value = messagesSentState.value.copy(
                                messagesSent = list,
                                isLoading = true,
                                isLoadingLocale = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _messagesSentState.value = messagesSentState.value.copy(
                            messagesSent = it.data ?: emptyList(),
                            page = 1,
                            isEverythingLoaded = false,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesSentState.value = messagesSentState.value.copy(isLoading = false,)
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

    fun initMessagesStarred(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            getMessagesStarred().collect {
                when (it) {
                    is Resource.Loading -> {
                        it.data?.let { list ->
                            _messagesStarredState.value = messagesStarredState.value.copy(
                                messagesStarred = list,
                                isLoading = true,
                                isLoadingLocale = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _messagesStarredState.value = messagesStarredState.value.copy(
                            messagesStarred = it.data ?: emptyList(),
                            page = 1,
                            isEverythingLoaded = false,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesStarredState.value = messagesStarredState.value.copy(isLoading = false,)
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

    fun initMessagesDeleted(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            getMessagesDeleted().collect {
                when (it) {
                    is Resource.Loading -> {
                        it.data?.let { list ->
                            _messagesDeletedState.value = messagesDeletedState.value.copy(
                                messagesDeleted = list,
                                isLoading = true,
                                isLoadingLocale = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _messagesDeletedState.value = messagesDeletedState.value.copy(
                            messagesDeleted = it.data ?: emptyList(),
                            page = 1,
                            isEverythingLoaded = false,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesDeletedState.value = messagesDeletedState.value.copy(isLoading = false,)
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

    fun initMessagesIndividual(id: String, coroutineScope: CoroutineScope, isSent: Boolean = false) {
        coroutineScope.launch {
            getMessageIndividual(id, isSent).collect {
                when (it) {
                    is Resource.Loading -> {
                        // NOTE: could show loading animation
                    }
                    is Resource.Success -> {
                        _messageIndividualFlow.emit(MessageIndividualState(it.data, false))
                    }
                    is Resource.Error -> {
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

    fun initMessagesGottenByCondition(coroutineScope: CoroutineScope) {
        if (_messagesGottenState.value.isLoading || !validator.hasInternetConnection(getApplication<ManoDienynasApp>())) return
        coroutineScope.launch {
            getMessagesGottenByCondition(_messagesGottenState.value.page).collect {
                when (it) {
                    is Resource.Loading -> {
                        _messagesGottenState.value = messagesGottenState.value.copy(
                            isLoading = true,
                        )
                    }
                    is Resource.Success -> {
                        val newList = _messagesGottenState.value.messagesGotten+(it.data ?: emptyList())
                        _messagesGottenState.value = messagesGottenState.value.copy(
                            messagesGotten = newList,
                            page = _messagesGottenState.value.page+1,
                            isEverythingLoaded = (it.data ?: emptyList()).isEmpty(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesGottenState.value = messagesGottenState.value.copy(isLoading = false,)
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

    fun initMessagesSentByCondition(coroutineScope: CoroutineScope) {
        if (_messagesSentState.value.isLoading || !validator.hasInternetConnection(getApplication<ManoDienynasApp>())) return
        coroutineScope.launch {
            getMessagesSentByCondition(_messagesSentState.value.page).collect {
                when (it) {
                    is Resource.Loading -> {
                        _messagesSentState.value = messagesSentState.value.copy(
                            isLoading = true,
                        )
                    }
                    is Resource.Success -> {
                        val newList = _messagesSentState.value.messagesSent+(it.data ?: emptyList())
                        _messagesSentState.value = messagesSentState.value.copy(
                            messagesSent = newList,
                            page = _messagesSentState.value.page+1,
                            isEverythingLoaded = (it.data ?: emptyList()).isEmpty(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesSentState.value = messagesSentState.value.copy(isLoading = false,)
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

    fun initMessagesStarredByCondition(coroutineScope: CoroutineScope) {
        if (_messagesStarredState.value.isLoading || !validator.hasInternetConnection(getApplication<ManoDienynasApp>())) return
        coroutineScope.launch {
            getMessagesStarredByCondition(_messagesStarredState.value.page).collect {
                when (it) {
                    is Resource.Loading -> {
                        _messagesStarredState.value = messagesStarredState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        val newList = _messagesStarredState.value.messagesStarred+(it.data ?: emptyList())
                        _messagesStarredState.value = messagesStarredState.value.copy(
                            messagesStarred = newList,
                            page = _messagesStarredState.value.page+1,
                            isEverythingLoaded = (it.data ?: emptyList()).isEmpty(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesStarredState.value = messagesStarredState.value.copy(isLoading = false,)
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

    fun initMessagesDeletedByCondition(coroutineScope: CoroutineScope) {
        if (_messagesDeletedState.value.isLoading || !validator.hasInternetConnection(getApplication<ManoDienynasApp>())) return
        coroutineScope.launch {
            getMessagesDeletedByCondition(_messagesDeletedState.value.page).collect {
                when (it) {
                    is Resource.Loading -> {
                        _messagesDeletedState.value = messagesDeletedState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        val newList = _messagesDeletedState.value.messagesDeleted+(it.data ?: emptyList())
                        _messagesDeletedState.value = messagesDeletedState.value.copy(
                            messagesDeleted = newList,
                            page = _messagesDeletedState.value.page+1,
                            isEverythingLoaded = (it.data ?: emptyList()).isEmpty(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _messagesDeletedState.value = messagesDeletedState.value.copy(isLoading = false,)
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

    fun resetLoadingState() {
        _messagesGottenState.value = messagesGottenState.value.copy(isLoading = false,)
        _messagesSentState.value = messagesSentState.value.copy(isLoading = false,)
        _messagesStarredState.value = messagesStarredState.value.copy(isLoading = false,)
        _messagesDeletedState.value = messagesDeletedState.value.copy(isLoading = false,)
    }

}