package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentManager
import com.sunnyoaklabs.manodienynas.R
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
    val datePickedFirst = remember {
        mutableStateOf("")
    }
    val datePickedSecond = remember {
        mutableStateOf("")
    }
    val updatedDate = { dateFirst : Long?, dateSecond : Long? ->
        marksFragmentViewModel.initDataByCondition()
        datePickedFirst.value = marksFragmentViewModel.formatDateFromMillis(dateFirst) ?: ""
        datePickedSecond.value = marksFragmentViewModel.formatDateFromMillis(dateSecond) ?: ""
    }
    DatePickerView(
        fragmentManager,
        datePickedFirst.value + " - " + datePickedSecond.value,
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
        colors = if (markFragmentTypeState.markTypeIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            marksFragmentViewModel.updateMarkMarkFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(5.dp)
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
        colors = if (markFragmentTypeState.controlWorkTypeIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            marksFragmentViewModel.updateControlWorkMarkFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(5.dp)
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
        colors = if (markFragmentTypeState.homeWorkTypeIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            marksFragmentViewModel.updateHomeWorkMarkFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(5.dp)
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
        colors = if (markFragmentTypeState.classWorkTypeIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            marksFragmentViewModel.updateClassWorkMarkFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(5.dp)
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

