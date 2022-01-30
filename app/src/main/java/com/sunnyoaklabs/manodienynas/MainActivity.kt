package com.sunnyoaklabs.manodienynas

import android.content.Intent
import androidx.activity.viewModels
import android.os.Bundle
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
        if (!viewModel.mainScreenState.value.isUserLoggedIn) {
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .putExtra("error", viewModel.errorMessage.value)
            )
        }
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
