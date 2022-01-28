package com.sunnyoaklabs.manodienynas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sunnyoaklabs.manodienynas.core.util.Errors.EMPTY_CREDENTIALS_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.EMPTY_SESSION_ID_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.UNKNOWN_ERROR
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.presentation.LoginScreen
import com.sunnyoaklabs.manodienynas.presentation.LoginViewModel
import com.sunnyoaklabs.manodienynas.presentation.MainViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManoDienynasTheme {
                // todo display toast using error gotten from intent
                //  1. add two textfield and button for login
                //  2. in view model get data from repository

                val errorMessage = intent.getStringExtra("error")
                val viewModel: LoginViewModel = hiltViewModel()
                val scaffoldState = rememberScaffoldState()

                LaunchedEffect(key1 = true) {
                    errorMessage?.let {
                        showErrorSnackbar(
                            scaffoldState,
                            it
                        )
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                    ) {
                        LoginScreen(Credentials("", ""))
                    }
                }
            }
        }
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
            EMPTY_SESSION_ID_ERROR -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = errorMessage
                )
            }
            EMPTY_CREDENTIALS_ERROR -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = errorMessage
                )
            }
        }
    }
}
