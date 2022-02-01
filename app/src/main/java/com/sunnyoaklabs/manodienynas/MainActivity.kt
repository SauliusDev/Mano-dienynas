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
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("console log", "main activity started 1 ${viewModel.until.value}")
        viewModel.until.value = true
        Log.e("console log", "main activity started 2 ${viewModel.until.value}")
        /** Handles initial login **/
        val intentExtra = intent.getStringExtra("initial")
        intentExtra?.let {
            viewModel.setInitialLogin(true)
        }
        /** Splash screen handling **/
        installSplashScreen().apply {
            setKeepVisibleCondition {
//                viewModel.mainScreenState.value.isLoading
                viewModel.until.value
            }
        }
        if (!viewModel.mainScreenState.value.isUserLoggedIn) {
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .putExtra("error", viewModel.errorMessage.value)
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
