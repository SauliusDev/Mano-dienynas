package com.sunnyoaklabs.manodienynas.presentation.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val app: Application,
    private val repository: Repository,
    private val dataSource: DataSource
) : ViewModel() {

    private val _credentials = MutableStateFlow(Credentials("", ""))
    val credentials: StateFlow<Credentials> = _credentials

    var keepSignedIn by mutableStateOf(false)
        private set

    init {
        getKeepSignedIn()
    }

    fun getCredentials() {
        viewModelScope.launch { _credentials.value = repository.getCredentials() }
    }

    fun insertCredentials(credentials: Credentials) {
        viewModelScope.launch { dataSource.insertCredentials(credentials) }
    }

    fun deleteCredentials() {
        viewModelScope.launch { dataSource.deleteCredentials() }
    }

    fun getKeepSignedIn() {
        viewModelScope.launch {
            keepSignedIn = repository.getSettings().keepSignedIn
        }
    }

    fun deleteKeepSignedIn() {
        viewModelScope.launch { dataSource.deleteSettings() }
    }

    fun insertKeepSignedIn(keepSignedIn: Boolean) {
        viewModelScope.launch { dataSource.insertSettings(Settings(keepSignedIn)) }
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