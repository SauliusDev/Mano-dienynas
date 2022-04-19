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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class TermsFragmentViewModel @Inject constructor(
    private val getTerm: GetTerm,
    private val getTermMarkDialogItem: GetTermMarkDialogItem,
    val validator: Validator
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _termState = mutableStateOf(TermState())
    val termState: State<TermState> = _termState

    private val _termMarkDialogItemFlow = MutableSharedFlow<TermMarkDialogItemState>()
    val termMarkDialogItemFlow = _termMarkDialogItemFlow.asSharedFlow()

    fun initTerm(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            getTerm().collect {
                when (it) {
                    is Resource.Loading -> {
                        it.data?.let { list ->
                            _termState.value = termState.value.copy(
                                terms = list,
                                isLoading = true,
                                isLoadingLocale = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        _termState.value = termState.value.copy(
                            terms = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _termState.value = termState.value.copy(isLoading = false,)
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

    fun initTermMarkDialogItem(url: String, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            getTermMarkDialogItem(url).collect {
                when (it) {
                    is Resource.Loading -> {
                        // NOTE: could show loading animation
                    }
                    is Resource.Success -> {
                        _termMarkDialogItemFlow.emit(TermMarkDialogItemState(it.data, false))
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

    fun resetLoadingState() {
        _termState.value = termState.value.copy(isLoading = false,)
    }
}