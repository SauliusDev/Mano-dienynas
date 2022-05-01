package com.sunnyoaklabs.manodienynas.presentation.login

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.MainActivity
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSettings
import com.sunnyoaklabs.manodienynas.presentation.main.state.CredentialsState
import com.sunnyoaklabs.manodienynas.presentation.main.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.BufferedReader
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val app: Application,
    private val repository: Repository,
    private val dataSource: DataSource,
    private val getSettings: GetSettings
) : ViewModel() {

    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var keepSignedIn by mutableStateOf(SettingsState())
        private set

    init {
        viewModelScope.launch {
            getCredentials()
            getKeepSignedIn()
        }
    }

    private suspend fun getCredentials() {
        val credentials = repository.getCredentials()
        username = credentials.username
        password = credentials.password
    }

    fun updateCredentialsOnType(username: String, password: String) {
        updateCredentialsNotValidated(Credentials(username, password))
    }

    fun updateUsernameOnType(username: String) {
        this.username = username
        updateCredentialsNotValidated(Credentials(username, password))
    }

    fun updatePasswordOnType(password: String) {
        this.password = password
        updateCredentialsNotValidated(Credentials(username, password))
    }

    private fun updateCredentialsNotValidated(credentials: Credentials): Job {
        return viewModelScope.launch {
            dataSource.deleteCredentials()
            dataSource.insertCredentials(Credentials(credentials.username, credentials.password, false))
        }
    }

    private suspend fun getKeepSignedIn() {
        getSettings().collect {
            when(it) {
                is Resource.Loading -> {
                    keepSignedIn = SettingsState(null, true)
                }
                is Resource.Success -> {
                    it.data?.keepSignedIn?.let { keepSignedInFromLocal ->
                        keepSignedIn = SettingsState(Settings(keepSignedInFromLocal), false)
                    }
                }
                else -> {}
            }
        }
    }

    private suspend fun deleteKeepSignedIn() {
        dataSource.deleteSettings()
    }

    private suspend fun insertKeepSignedIn(keepSignedIn: Boolean) {
        dataSource.insertSettings(Settings(keepSignedIn))
    }

    fun updateKeepSignedIn(keepSignedIn: Boolean) {
        viewModelScope.launch {
            deleteKeepSignedIn()
            insertKeepSignedIn(keepSignedIn)
            getKeepSignedIn()
        }
    }

    fun getAppDescription(): String {
        return app.resources.openRawResource(R.raw.app_description)
            .bufferedReader().use(BufferedReader::readText)
    }

    fun getAppLicense(): String {
        return app.resources.openRawResource(R.raw.app_license)
            .bufferedReader().use(BufferedReader::readText)
    }

}