package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentManager
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.isDarkThemeOn
import com.sunnyoaklabs.manodienynas.presentation.core.getButtonColor
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.dialog.DatePickerView
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MarksFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.*

@Composable
fun ChangeDateButton(
    marksFragmentViewModel: MarksFragmentViewModel,
    fragmentManager: FragmentManager,
    modifier: Modifier = Modifier
) {
    val markFragmentTypeState = marksFragmentViewModel.markFragmentTypeState.value
    val markDateRange = marksFragmentViewModel.markTimeRange.value
    val controlWorkDateRange = marksFragmentViewModel.controlWorkTimeRange.value
    val homeWorkDateRange = marksFragmentViewModel.homeWorkTimeRange.value
    val classWorkDateRange = marksFragmentViewModel.classWorkTimeRange.value
    val dateRangePicked = remember {
        mutableStateOf(markDateRange)
    }
    when {
        markFragmentTypeState.markTypeIsSelected -> {
            dateRangePicked.value = markDateRange.copy(
                markDateRange.first,
                markDateRange.second
            )
        }
        markFragmentTypeState.controlWorkTypeIsSelected -> {
            dateRangePicked.value = controlWorkDateRange.copy(
                controlWorkDateRange.first,
                controlWorkDateRange.second
            )
        }
        markFragmentTypeState.homeWorkTypeIsSelected -> {
            dateRangePicked.value = homeWorkDateRange.copy(
                homeWorkDateRange.first,
                homeWorkDateRange.second
            )
        }
        markFragmentTypeState.classWorkTypeIsSelected -> {
            dateRangePicked.value = classWorkDateRange.copy(
                classWorkDateRange.first,
                classWorkDateRange.second
            )
        }
    }
    val updatedDate = { dateFirst : Long?, dateSecond : Long? ->
        val timeRangeFormatted = marksFragmentViewModel.formatTimeRange(
            Pair(dateFirst ?: "".toLong(), dateSecond ?: "".toLong())
        )
        marksFragmentViewModel.updateTimeRange(timeRangeFormatted)
        marksFragmentViewModel.initDataByCondition()
        dateRangePicked.value = timeRangeFormatted
    }
    DatePickerView(
        fragmentManager,
        dateRangePicked.value.first + " - " + dateRangePicked.value.second,
        updatedDate,
        modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
    )
}

@Composable
fun MarksCardButton(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val markFragmentTypeState = marksFragmentViewModel.markFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = getButtonColor(markFragmentTypeState.markTypeIsSelected),
        onClick = {
            marksFragmentViewModel.updateMarkMarkFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_mark),
                contentDescription = stringResource(R.string.ic_mark_description),
                tint = accentRed
            )
        }
    }
}

@Composable
fun ControlWorkCardButton(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val markFragmentTypeState = marksFragmentViewModel.markFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = getButtonColor(markFragmentTypeState.controlWorkTypeIsSelected),
        onClick = {
            marksFragmentViewModel.updateControlWorkMarkFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_controlwork),
                contentDescription = stringResource(R.string.ic_control_work_description),
                tint = accentYellowDark
            )
        }
    }
}

@Composable
fun HomeWorkCardButton(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val markFragmentTypeState = marksFragmentViewModel.markFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = getButtonColor(markFragmentTypeState.homeWorkTypeIsSelected),
        onClick = {
            marksFragmentViewModel.updateHomeWorkMarkFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_homework),
                contentDescription = stringResource(R.string.ic_homework_description),
                tint = accentBlue
            )
        }
    }
}

@Composable
fun ClassWorkCardButton(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val markFragmentTypeState = marksFragmentViewModel.markFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = getButtonColor(markFragmentTypeState.classWorkTypeIsSelected),
        onClick = {
            marksFragmentViewModel.updateClassWorkMarkFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_class_work),
                contentDescription = stringResource(R.string.ic_classwork_description),
                tint = accentBlueLight
            )
        }
    }
}

