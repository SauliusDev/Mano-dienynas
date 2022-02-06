package com.sunnyoaklabs.manodienynas

import android.content.Intent
import androidx.activity.viewModels
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.SplashViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Handles initial login **/
        val intentExtra = intent.getStringExtra("initial")
        intentExtra?.let {
            splashViewModel.setInitialLogin(true)
        }
        /** Splash screen handling **/
        installSplashScreen().apply {
            setKeepVisibleCondition {
                splashViewModel.userState.value.isLoading
            }
        }
        Log.e("console log", "main: "+splashViewModel.userState.value.isUserLoggedIn)
        if (!splashViewModel.userState.value.isUserLoggedIn) {
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .putExtra("error", splashViewModel.errorMessage.value)
            )
        }
        /** MainActivity content **/
        setContent {
            ManoDienynasTheme {
                Text(text = "This is main activity", modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 100.dp, vertical = 100.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
