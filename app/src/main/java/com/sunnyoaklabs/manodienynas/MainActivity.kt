package com.sunnyoaklabs.manodienynas

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.viewModels
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
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
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.START_ACTIVITY_LOGIN_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.UIEvent
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.Screen
import com.sunnyoaklabs.manodienynas.presentation.main.SplashViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.bottomNavigationItems
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.*
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.events.EventsFragment
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.MarksFragment
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.messages.MessagesFragment
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.more.MoreFragment
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.terms.TermsFragment
import com.sunnyoaklabs.manodienynas.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
                        ToolbarMain(mainViewModel.isDataBeingLoaded, this, navController)
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
            is UIEvent.StartActivity -> {
                when(event.message) {
                    START_ACTIVITY_LOGIN_EVENT_TYPE -> {
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        this.startActivity(intent)
                    }
                }
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
                        text = LocalContext.current.resources.getString(screen.title),
                        color = accentGreenDarkest,
                        fontSize = if (LocalContext.current.resources.getString(screen.title).length >= 9) {
                            11.sp
                        } else {
                            12.sp
                        }
                    )
                },
                selected = currentRoute == screen.route,
                alwaysShowLabel = false,
                onClick = {
                    when (screen.route) {
                        "events" -> {
                            mainViewModel.eventsFragmentViewModel.onFragmentOpen()
                            navController.navigate(Screen.Events.route)
                        }
                        "marks" -> {
                            navController.navigate(Screen.Marks.route)
                        }
                        "messages" -> {
                            navController.navigate(Screen.Messages.route)
                        }
                        "terms" -> {
                            navController.navigate(Screen.Terms.route)
                        }
                        "more" -> {
                            navController.navigate(Screen.More.route)
                        }
                        "settings" -> {
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
    isDataBeingLoaded: State<Boolean>,
    context: Context,
    navController: NavHostController
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val imageLoader = ImageLoader.Builder(context)
                    .components {
                        if (Build.VERSION.SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()
                if (isDataBeingLoaded.value) {
                    Image(
                        painter = rememberImagePainter(data = R.drawable.loading_animation, imageLoader = imageLoader),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp)
                    )
                } else {
                    Image(painter = painterResource(id = R.drawable.ic_data_downloaded), contentDescription = "")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = stringResource(id = R.string.app_name), color = primaryGreenAccent)
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
