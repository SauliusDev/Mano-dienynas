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
