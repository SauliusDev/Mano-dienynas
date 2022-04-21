package com.sunnyoaklabs.manodienynas

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navArgs
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ramcosta.composedestinations.DestinationsNavHost
import com.sunnyoaklabs.manodienynas.core.util.Errors
import com.sunnyoaklabs.manodienynas.core.util.Errors.INCORRECT_CREDENTIALS
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.NULL_OBJECT_RECEIVED_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.presentation.login.LoginViewModel
import com.sunnyoaklabs.manodienynas.presentation.login.fragment.NavGraphs
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManoDienynasTheme {
                val intentExtra = intent.getStringExtra("error")
                val scaffoldState = rememberScaffoldState()
                LaunchedEffect(key1 = true) {
                    intentExtra?.let {
                        showErrorSnackbar(scaffoldState, it)
                    }
                }
                Scaffold(
                    scaffoldState = scaffoldState,
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
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
                    message = resources.getString(R.string.snackbar_unknown_error),
                    duration = SnackbarDuration.Short
                )
            }
            IO_ERROR -> {
                FirebaseCrashlytics.getInstance().log("(LoginActivity) IO_ERROR")
                scaffoldState.snackbarHostState.showSnackbar(
                    message = resources.getString(R.string.snackbar_no_network),
                    duration = SnackbarDuration.Short
                )
            }
            INCORRECT_CREDENTIALS -> {
                FirebaseCrashlytics.getInstance().log("(LoginActivity) INCORRECT_CREDENTIALS")
                scaffoldState.snackbarHostState.showSnackbar(
                    message = resources.getString(R.string.snackbar_incorrect_credentials),
                    duration = SnackbarDuration.Short
                )
            }
            NULL_OBJECT_RECEIVED_ERROR -> {
                FirebaseCrashlytics.getInstance().log("(LoginActivity) NULL_OBJECT_RECEIVED_ERROR")
            }
        }
    }

}
