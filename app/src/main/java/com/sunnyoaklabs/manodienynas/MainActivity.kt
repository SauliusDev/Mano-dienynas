package com.sunnyoaklabs.manodienynas

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.sunnyoaklabs.manodienynas.core.util.Errors.IO_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Errors.TIMEOUT_ERROR
import com.sunnyoaklabs.manodienynas.core.util.Fragments.EVENTS_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MARKS_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MESSAGES_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MORE_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.SETTINGS_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.TERMS_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.core.util.EventUITypes.START_ACTIVITY_LOGIN_EVENT_UI_TYPE
import com.sunnyoaklabs.manodienynas.presentation.core.getBottomNavTextColor
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.Screen
import com.sunnyoaklabs.manodienynas.presentation.main.bottomNavigationItems
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.settings.SettingsMainFragment
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.events.EventsFragment
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.MarksFragment
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.messages.MessagesFragment
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.more.MoreFragment
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.terms.TermsFragment
import com.sunnyoaklabs.manodienynas.presentation.splash.SplashViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import com.sunnyoaklabs.manodienynas.ui.theme.accentGreenDarkest
import com.sunnyoaklabs.manodienynas.ui.theme.primaryGreenAccent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val splashViewModel: SplashViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
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
                splashViewModel.userStateSplash.value.isLoading
            }
        }
        lifecycleScope.launch {
            splashViewModel.runSplash()
            splashViewModel.userStateSplash.collect {
                if (!it.isUserLoggedIn && !it.isLoading) {
                    startActivityLogin(this@MainActivity)
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
                    mainViewModel.eventFlow.collectLatest {
                        processEvent(it, scaffoldState)
                    }
                }

                Scaffold(
                    topBar = {
                        ToolbarMain(mainViewModel, this, navController)
                    },
                    bottomBar = {
                        BottomNavigationBar(navController, mainViewModel, bottomNavigationItems)
                    },
                    scaffoldState = scaffoldState
                ) { innerPadding ->
                    NavHostContainer(navController, innerPadding)
                }
            }
        }
    }

    @Composable
    fun NavHostContainer(
        navController: NavHostController,
        padding: PaddingValues
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Events.route,
            modifier = Modifier.padding(paddingValues = padding),
            builder = {
                composable(Screen.Events.route) {
                    EventsFragment(mainViewModel)
                }
                composable(Screen.Marks.route) {
                    MarksFragment(
                        mainViewModel,
                        (this@MainActivity as AppCompatActivity).supportFragmentManager,
                    )
                }
                composable(Screen.Messages.route) {
                    MessagesFragment(mainViewModel)
                }
                composable(Screen.Terms.route) {
                    TermsFragment(mainViewModel)
                }
                composable(Screen.More.route) {
                    MoreFragment(mainViewModel)
                }
                composable(Screen.Settings.route) {
                    SettingsMainFragment(mainViewModel)
                }
            }
        )
    }

    private fun startActivityLogin(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
            .putExtra("error", splashViewModel.errorMessage)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent)
    }

    private suspend fun processEvent(
        event: UIEvent,
        scaffoldState: ScaffoldState
    ) {
        when (event) {
            is UIEvent.ShowToast -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = processSnackbarMessage(event.message),
                    duration = SnackbarDuration.Short
                )
            }
            is UIEvent.StartActivity -> {
                when(event.message) {
                    START_ACTIVITY_LOGIN_EVENT_UI_TYPE -> {
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        this.startActivity(intent)
                    }
                }
            }
        }
    }

    private fun processSnackbarMessage(message: String): String {
        return when(message) {
            IO_ERROR -> {
                resources.getString(R.string.snackbar_no_network)
            }
            TIMEOUT_ERROR -> {
                resources.getString(R.string.snackbar_timeout)
            }
            else -> {
                resources.getString(R.string.snackbar_unknown_error)
            }
        }
    }

}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    bottomNavigationItems: List<Screen>
) {
    BottomNavigation(
        backgroundColor = primaryGreenAccent,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomNavigationItems.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = LocalContext.current.resources.getString(screen.title),
                        tint = accentGreenDarkest
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.title),
                        color = getBottomNavTextColor(),
                        fontSize = when(stringResource(screen.title)) {
                            stringResource(id = R.string.bottom_nav_messages) -> 10.sp
                            stringResource(id = R.string.bottom_nav_terms) -> 11.sp
                            else -> 12.sp
                        }
                    )
                },
                selected = currentRoute == screen.route,
                alwaysShowLabel = false,
                onClick = {
                    when (screen.route) {
                        "events" -> {
                            mainViewModel.onFragmentOpen(EVENTS_FRAGMENT)
                            navController.navigate(Screen.Events.route)
                        }
                        "marks" -> {
                            mainViewModel.onFragmentOpen(MARKS_FRAGMENT)
                            navController.navigate(Screen.Marks.route)
                        }
                        "messages" -> {
                            mainViewModel.onFragmentOpen(MESSAGES_FRAGMENT)
                            navController.navigate(Screen.Messages.route)
                        }
                        "terms" -> {
                            mainViewModel.onFragmentOpen(TERMS_FRAGMENT)
                            navController.navigate(Screen.Terms.route)
                        }
                        "more" -> {
                            mainViewModel.onFragmentOpen(MORE_FRAGMENT)
                            navController.navigate(Screen.More.route)
                        }
                        "settings" -> {
                            mainViewModel.onFragmentOpen(SETTINGS_FRAGMENT)
                            navController.navigate(Screen.Settings.route)
                        }
                    }
                }
            )
        }
    }
}

sealed class MenuAction(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @ColorInt val color: Color
) {
    object Settings : MenuAction(R.string.settings, R.drawable.ic_settings, accentGreenDarkest)
}

@Composable
fun ToolbarMain(
    mainViewModel: MainViewModel,
    context: Context,
    navController: NavHostController
) {
    var isDataBeingLoaded by remember {
        mutableStateOf(false)
    }
    isDataBeingLoaded = checkIfLoading(mainViewModel = mainViewModel)
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                val imageLoader = ImageLoader.Builder(context)
                    .components {
                        if (Build.VERSION.SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()
                if (isDataBeingLoaded) {
                    Image(
                        painter = rememberImagePainter(data = R.drawable.loading_animation, imageLoader = imageLoader),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_data_synched),
                        contentDescription = "",
                        tint = accentGreenDarkest,
                        modifier = Modifier.size(25.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = stringResource(id = R.string.app_name_long), color = accentGreenDarkest)
            }
        },
        backgroundColor = primaryGreenAccent,
        actions = {
            AppBarIcon(
                MenuAction.Settings
            ) {
                navController.navigate(Screen.Settings.route)
            }
        }
    )
}

@Composable
fun AppBarIcon(
    menuAction: MenuAction,
    function: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(end = 10.dp),
        onClick = {
            function.invoke()
        },
    ) {
        Icon(
            painter = painterResource(id = menuAction.icon),
            contentDescription = stringResource(id = menuAction.label),
            tint = menuAction.color
        )
    }
}

private fun checkIfLoading(mainViewModel: MainViewModel): Boolean {
    return mainViewModel.eventsFragmentViewModel.eventState.value.isLoading ||
        mainViewModel.marksFragmentViewModel.markState.value.isLoading ||
        mainViewModel.marksFragmentViewModel.controlWorkState.value.isLoading ||
        mainViewModel.marksFragmentViewModel.homeWorkState.value.isLoading ||
        mainViewModel.marksFragmentViewModel.classWorkState.value.isLoading ||
        mainViewModel.messagesFragmentViewModel.messagesGottenState.value.isLoading ||
        mainViewModel.messagesFragmentViewModel.messagesSentState.value.isLoading ||
        mainViewModel.messagesFragmentViewModel.messagesStarredState.value.isLoading ||
        mainViewModel.messagesFragmentViewModel.messagesDeletedState.value.isLoading ||
        mainViewModel.termsFragmentViewModel.termState.value.isLoading ||
        mainViewModel.moreFragmentViewModel.scheduleState.value.isLoading ||
        mainViewModel.moreFragmentViewModel.holidayState.value.isLoading ||
        mainViewModel.moreFragmentViewModel.parentMeetingState.value.isLoading ||
        mainViewModel.userStateState.value.isLoading
}