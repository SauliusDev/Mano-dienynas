package com.sunnyoaklabs.manodienynas.presentation.core

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.isDarkThemeOn
import com.sunnyoaklabs.manodienynas.ui.theme.*

@Composable
fun getButtonColor(isSelected: Boolean): ButtonColors {
    return ButtonDefaults.buttonColors(if (isSelected) {
        if (LocalContext.current.isDarkThemeOn()) {
            accentBlackLighter
        } else {
            accentGreenLightest
        }
    } else {
        if (LocalContext.current.isDarkThemeOn()) {
            accentBlack
        } else {
            accentGrey
        }
    })
}

@Composable
fun getDatePickerColor(): ButtonColors {
    return ButtonDefaults.buttonColors(if(LocalContext.current.isDarkThemeOn()) {
        accentBlack
    } else {
        accentGrey
    })
}

@Composable
fun getMarksListItemColor(isCollapsed: Boolean): Int {
    return if (isCollapsed) {
        android.R.color.transparent
    } else {
        if (LocalContext.current.isDarkThemeOn()) {
            R.color.accent_black_lighter
        } else {
            R.color.accent_grey
        }
    }
}

@Composable
fun getTermItemColor(isCollapsed: Boolean): Int {
    return if (isCollapsed) {
        android.R.color.transparent
    } else {
        if (LocalContext.current.isDarkThemeOn()) {
            R.color.accent_black_lighter
        } else {
            R.color.accent_grey
        }
    }
}

@Composable
fun getBottomNavTextColor(): Color {
    return if(LocalContext.current.isDarkThemeOn()) {
        accentGreenDarkest
    } else {
        accentGreenDarkest
    }
}




