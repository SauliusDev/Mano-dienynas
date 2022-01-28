package com.sunnyoaklabs.manodienynas

import android.content.Intent
import androidx.activity.viewModels
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.presentation.LoginScreen
import com.sunnyoaklabs.manodienynas.presentation.MainViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepVisibleCondition {
                viewModel.mainScreenState.value.isLoading
            }
        }
//        if (!viewModel.mainScreenState.value.isUserLoggedIn) {
//            startActivity(
//                Intent(this, LoginActivity::class.java)
//                    .putExtra("error", viewModel.errorMessage.value)
//            )
//        }
        setContent {
            ManoDienynasTheme {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                ) {
                    LoginScreen(credentials = Credentials("", ""))
                }
            }
        }
    }
}
