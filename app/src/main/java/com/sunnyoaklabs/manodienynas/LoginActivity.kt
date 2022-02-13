package com.sunnyoaklabs.manodienynas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ramcosta.composedestinations.DestinationsNavHost
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.NULL_OBJECT_RECEIVED_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.presentation.login.fragment.NavGraphs
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import dagger.hilt.android.AndroidEntryPoint


/*
    todo workflow (main set up):
        <> add navGraph name (login, main)

        <> get session in mainViewModel
        <> small height toolbar with username
        <> bottom navigation with all fragments
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
