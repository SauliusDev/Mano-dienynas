package com.sunnyoaklabs.manodienynas.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: Repository
): ViewModel()  {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _repositoryCredentials = repository.getCredentials()

    init {
        viewModelScope.launch {
            /*
             things to check:
             - is login information gotten
                true -> MainActivity
                false -> loginActivity
             */
            _repositoryCredentials.collect {
                when(it) {
                    is Resource.Success -> {
                        // todo request new session id and launch app
                        FirebaseCrashlytics.getInstance().log("Success: transferring user to main activity")
                    }
                    is Resource.Error -> {
                        FirebaseCrashlytics.getInstance().log("Error: transferring user to login activity")
                        // todo go to login
                    }
                    is Resource.Loading -> {
                        FirebaseCrashlytics.getInstance().log("Loading: credentials")
                    }
                }
            }



            _isLoading.value = false
        }
    }

}