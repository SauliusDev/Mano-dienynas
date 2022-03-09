package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp

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
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MarksFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.*

@Composable
fun MarksCardButton(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val markFragmentTypeState = marksFragmentViewModel.markFragmentTypeState.value
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        colors = if (markFragmentTypeState.markTypeIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            marksFragmentViewModel.updateMarkMarkFragmentTypeState()
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_mark),
                contentDescription = stringResource(R.string.ic_mark_description),
                tint = accentRed
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = R.string.marks_fragment_marks),
                color = accentRed,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
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
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        colors = if (markFragmentTypeState.controlWorkTypeIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            marksFragmentViewModel.updateControlWorkMarkFragmentTypeState()
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_mark),
                contentDescription = stringResource(R.string.ic_mark_description),
                tint = accentYellowDark
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = R.string.marks_fragment_control_work),
                color = accentYellowDark,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
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
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        colors = if (markFragmentTypeState.homeWorkTypeIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            marksFragmentViewModel.updateHomeWorkMarkFragmentTypeState()
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_mark),
                contentDescription = stringResource(R.string.ic_mark_description),
                tint = accentBlue
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = R.string.marks_fragment_homework),
                color = accentBlue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
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
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        colors = if (markFragmentTypeState.classWorkTypeIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            marksFragmentViewModel.updateClassWorkMarkFragmentTypeState()
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_mark),
                contentDescription = stringResource(R.string.ic_mark_description),
                tint = accentBlueLight
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = R.string.marks_fragment_class_work),
                color = accentBlueLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
            )
        }
    }
}