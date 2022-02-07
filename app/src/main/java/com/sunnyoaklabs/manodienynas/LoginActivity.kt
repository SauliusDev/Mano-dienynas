package com.sunnyoaklabs.manodienynas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.NULL_OBJECT_RECEIVED_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.presentation.login.LoginViewModel
import com.sunnyoaklabs.manodienynas.presentation.login.composable.LoginScreenComp
import com.sunnyoaklabs.manodienynas.presentation.login.composable.SettingsScreenComp
import com.sunnyoaklabs.manodienynas.presentation.main.SplashViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import dagger.hilt.android.AndroidEntryPoint


/*
    todo workflow (main set up):
        <> get session in mainViewModel
        <> small height toolbar with username
        <> bottom navigation with all fragrments
        <> settings on fragment done, options (all as in login + logout)
        <> logout works

    todo (session cookies refreshing based on response):
        <> make it :D

    todo (events screen):
        <> make events screen lol

    todo (ktor all get requests) for all other fragments:
        <> make those requests

    todo (jsoup web scrapping) for all other fragments:
        <> make all web scrapping functions lol
 */
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManoDienynasTheme {
                val intentExtra = intent.getStringExtra("error")
                val scaffoldState = rememberScaffoldState()

                LaunchedEffect(key1 = true) {
                    intentExtra?.let {
                        showErrorSnackbar(
                            scaffoldState,
                            it
                        )
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

@Destination(start = true)
@Composable
fun LoginScreen(navigator: DestinationsNavigator) {
    LoginScreenComp(navigator)
}

@Destination
@Composable
fun SettingScreen(navigator: DestinationsNavigator, ) {
    SettingsScreenComp(navigator)
}

private suspend fun showErrorSnackbar(
    scaffoldState: ScaffoldState,
    errorMessage: String
) {
    when (errorMessage) {
        UNKNOWN_ERROR -> {
            FirebaseCrashlytics.getInstance().log("(LoginActivity) UNKNOWN_ERROR")
            scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Short
            )
        }
        IO_ERROR -> {
            FirebaseCrashlytics.getInstance().log("(LoginActivity) IO_ERROR")
            scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Short
            )
        }
        NULL_OBJECT_RECEIVED_ERROR -> {
            FirebaseCrashlytics.getInstance().log("(LoginActivity) NULL_OBJECT_RECEIVED_ERROR")
        }
    }
}
