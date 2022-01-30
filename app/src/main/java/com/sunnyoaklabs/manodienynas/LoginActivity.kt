package com.sunnyoaklabs.manodienynas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sunnyoaklabs.manodienynas.core.util.Errors.NULL_OBJECT_RECEIVED_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.model.UserSettings
import com.sunnyoaklabs.manodienynas.presentation.login.composable.LoginScreen
import com.sunnyoaklabs.manodienynas.presentation.login.LoginViewModel
import com.sunnyoaklabs.manodienynas.presentation.login.composable.SettingsScreen
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManoDienynasTheme {
                val errorMessage = intent.getStringExtra("error")
                val scaffoldState = rememberScaffoldState()

                LaunchedEffect(key1 = true) {
                    errorMessage?.let {
                        showErrorSnackbar(
                            scaffoldState,
                            it
                        )
                    }
                }

                // todo workflow
                //      1. navigation works
                //      2. login works with needed tokens gotten

//                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }

    @Destination(start = true)
    @Composable
    fun LoginScreen(
        navigator: DestinationsNavigator
    ) {
        LoginScreen(
            navigator,
            Credentials("", "")
        )
    }

    @Destination
    @Composable
    fun SettingScreen(
        navigator: DestinationsNavigator
    ) {
        SettingsScreen(
            navigator,
            viewModel.getAppDescription(),
            viewModel.getAppLicense()
        )
    }

    private suspend fun showErrorSnackbar(
        scaffoldState: ScaffoldState,
        errorMessage: String
    ) {
        when(errorMessage) {
            UNKNOWN_ERROR -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = errorMessage
                )
            }
            IO_ERROR -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = errorMessage
                )
            }
            NULL_OBJECT_RECEIVED_ERROR -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = errorMessage
                )
            }
        }
    }
}
