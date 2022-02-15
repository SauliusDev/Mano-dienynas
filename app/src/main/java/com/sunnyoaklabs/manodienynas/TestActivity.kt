package com.sunnyoaklabs.manodienynas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sunnyoaklabs.manodienynas.core.util.DispatcherProvider
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.local.DataSourceImpl
import com.sunnyoaklabs.manodienynas.data.util.Converter
import com.sunnyoaklabs.manodienynas.data.util.JsoupWebScrapper
import com.sunnyoaklabs.manodienynas.domain.model.Mark
import com.sunnyoaklabs.manodienynas.domain.model.MarkEvent
import com.sunnyoaklabs.manodienynas.domain.model.SchoolInfo
import com.sunnyoaklabs.manodienynas.domain.model.User
import com.sunnyoaklabs.manodienynas.presentation.main.SplashViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.BufferedReader
import javax.inject.Inject

// TODO delete later
class TestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {}

        JsoupWebScrapper().toControlWork(
            this.resources.openRawResource(R.raw.controlwork_html)
                .bufferedReader().use(BufferedReader::readText)
        )
    }
}
