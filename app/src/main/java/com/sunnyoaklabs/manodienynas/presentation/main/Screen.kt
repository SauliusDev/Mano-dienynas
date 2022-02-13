package com.sunnyoaklabs.manodienynas.presentation.main

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.sunnyoaklabs.manodienynas.R

sealed class Screen(var title: Int, var icon: Int, var route: String){
    object Events : Screen(R.string.bottom_nav_events, R.drawable.ic_event,"events")
    object Marks: Screen(R.string.bottom_nav_marks,R.drawable.ic_mark,"marks")
    object Messages: Screen(R.string.bottom_nav_messages,R.drawable.ic_message,"messages")
    object More: Screen(R.string.bottom_nav_more,R.drawable.ic_more,"more")
    object Settings: Screen(R.string.bottom_nav_settings, R.drawable.ic_settings,"settings")
}

val bottomNavigationItems = listOf(
    Screen.Events,
    Screen.Marks,
    Screen.Messages,
    Screen.More,
    Screen.Settings
)