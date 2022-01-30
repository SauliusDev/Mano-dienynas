package com.sunnyoaklabs.manodienynas.presentation.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.core.util.toUserSettings
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.remote.BackendApi
import com.sunnyoaklabs.manodienynas.domain.model.UserSettings
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.BufferedReader
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val app: Application,
    private val repository: Repository,
    private val dataSource: DataSource
) : ViewModel() {

    var keepSignedIn by mutableStateOf(false)
        private set

    init {
        getKeepSignedIn()
    }

    fun getKeepSignedIn() {
        viewModelScope.launch { keepSignedIn = repository.getUserSetting().keepSignedIn }
    }

    fun deleteKeepSignedIn() {
        viewModelScope.launch { dataSource.deleteUserSetting() }
    }

    fun insertKeepSignedIn(keepSignedIn: Boolean) {
        viewModelScope.launch { dataSource.insertUserSetting(UserSettings(keepSignedIn)) }
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