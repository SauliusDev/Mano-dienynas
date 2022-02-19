package com.sunnyoaklabs.manodienynas

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.Screen
import com.sunnyoaklabs.manodienynas.presentation.main.SplashViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.bottomNavigationItems
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.*
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.*
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import com.sunnyoaklabs.manodienynas.ui.theme.primaryGreenAccent
import com.sunnyoaklabs.manodienynas.ui.theme.primaryVariantGreenLight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Handles initial login **/
        splashViewModel
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
        lifecycleScope.launch {
            splashViewModel.userState.collect {
                if (!it.isUserLoggedIn && !it.isLoading) {
                    startActivityLogin(this@MainActivity, splashViewModel.errorMessage)
                } else if(!it.isLoading) {
                    mainViewModel.initSessionCookies()
                }
            }
        }
        /** MainActivity content **/
        setContent {
            ManoDienynasTheme {
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()

                LaunchedEffect(key1 = true) {
                    collectEvents(scaffoldState)
                }

                Scaffold(
                    topBar = {
                        ToolbarMain()
                    },
                    bottomBar = {
                        BottomNavigationBar(navController, bottomNavigationItems)
                    },
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Events.route
                    ) {
                        composable(Screen.Events.route) {
                            EventsFragment(mainViewModel)
                        }
                        composable(Screen.Marks.route) {
                            MarksFragment()
                        }
                        composable(Screen.Messages.route) {
                            MessagesFragment()
                        }
                        composable(Screen.More.route) {
                            MoreFragment()
                        }
                        composable(Screen.Settings.route) {
                            SettingsMainFragment(mainViewModel)
                        }
                    }
                }
            }
        }
    }

    private fun startActivityLogin(context: Context, message: String) {
        val intent = Intent(context, LoginActivity::class.java)
            .putExtra("error", message)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent)
    }

    private suspend fun collectEvents(
        scaffoldState: ScaffoldState
    ) {
        mainViewModel.eventFlow.collectLatest {
            processEvent(it, scaffoldState)
        }
        mainViewModel.eventsFragmentViewModel.eventFlow.collectLatest {
            processEvent(it, scaffoldState)
        }
        mainViewModel.marksFragmentViewModel.eventFlow.collectLatest {
            processEvent(it, scaffoldState)
        }
        mainViewModel.messagesFragmentViewModel.eventFlow.collectLatest {
            processEvent(it, scaffoldState)
        }
        mainViewModel.moreFragmentViewModel.eventFlow.collectLatest {
            processEvent(it, scaffoldState)
        }
        mainViewModel.settingsMainFragmentViewModel.eventFlow.collectLatest {
            processEvent(it, scaffoldState)
        }
    }

    private suspend fun processEvent(
        event: UIEvent,
        scaffoldState: ScaffoldState
    ) {
        when (event) {
            is UIEvent.ShowSnackbar -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = event.message
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    bottomNavigationItems: List<Screen>
) {
    BottomNavigation(
        backgroundColor = primaryVariantGreenLight,
    ) {
        bottomNavigationItems.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = LocalContext.current.resources.getString(screen.title),
                        tint = Color.White
                    )
                },
                label = {
                    Text(
                        text = LocalContext.current.resources.getString(screen.title),
                        color = Color.White
                    )
                },
                selected = false,
                alwaysShowLabel = false,
                onClick = {
                    when (screen.route) {
                        "events" -> navController.navigate(Screen.Events.route)
                        "marks" -> navController.navigate(Screen.Marks.route)
                        "messages" -> navController.navigate(Screen.Messages.route)
                        "more" -> navController.navigate(Screen.More.route)
                        "settings" -> navController.navigate(Screen.Settings.route)
                    }
                }
            )
        }
    }
}

@Composable
fun ToolbarMain() {
    TopAppBar(
        title = {
            Text(
                text = "MainActivity",
                color = colorResource(id = android.R.color.white)
            )
        },
        backgroundColor = primaryVariantGreenLight,
    )
}