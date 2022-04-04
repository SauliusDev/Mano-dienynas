package com.sunnyoaklabs.manodienynas.presentation.main.fragment.more.comp

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MoreFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.*

@Composable
fun ScheduleCardButton(
    moreFragmentViewModel: MoreFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val moreFragmentTypeState = moreFragmentViewModel.moreFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = if (moreFragmentTypeState.scheduleIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            moreFragmentViewModel.updateScheduleMoreFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_schedule),
                contentDescription = stringResource(R.string.ic_schedule_description),
                tint = accentYellowDark
            )
        }
    }
}

@Composable
fun CalendarCardButton(
    moreFragmentViewModel: MoreFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val moreFragmentTypeState = moreFragmentViewModel.moreFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = if (moreFragmentTypeState.calendarIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            moreFragmentViewModel.updateCalendarMoreFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_calendar_more),
                contentDescription = stringResource(R.string.ic_calendar_more_description),
                tint = primaryVariantGreenLight
            )
        }
    }
}

@Composable
fun HolidayCardButton(
    moreFragmentViewModel: MoreFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val moreFragmentTypeState = moreFragmentViewModel.moreFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = if (moreFragmentTypeState.holidayIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            moreFragmentViewModel.updateHolidayMoreFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_holiday),
                contentDescription = stringResource(R.string.ic_holiday_description),
                tint = accentCyan
            )
        }
    }
}

@Composable
fun ParentMeetingsCardButton(
    moreFragmentViewModel: MoreFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val moreFragmentTypeState = moreFragmentViewModel.moreFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = if (moreFragmentTypeState.parentMeetingsIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            moreFragmentViewModel.updateParentMeetingsMoreFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_parent_meetings),
                contentDescription = stringResource(R.string.ic_parent_meetings_description),
                tint = accentBlueLight
            )
        }
    }
}

