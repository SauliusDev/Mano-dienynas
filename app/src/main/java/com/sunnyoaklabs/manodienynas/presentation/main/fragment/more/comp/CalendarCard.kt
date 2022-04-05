package com.sunnyoaklabs.manodienynas.presentation.main.fragment.more.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendarDto
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingList
import com.sunnyoaklabs.manodienynas.presentation.core.disableScrolling
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MoreFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.accentBlueLight
import com.sunnyoaklabs.manodienynas.ui.theme.primaryVariantGreenLight

//@Composable
//fun CalendarCard(
//    moreFragmentViewModel: MoreFragmentViewModel,
//    modifier: Modifier = Modifier
//) {
//    val calendarState = moreFragmentViewModel.calendarState.value
//
//    val scope = rememberCoroutineScope()
//    val state = rememberLazyListState()
//    state.disableScrolling(scope)
//    when {
//        calendarState.isLoading -> {
//            LoadingList(10, state)
//        }
//        calendarState.calendar.isEmpty() -> {
//            EmptyCalendarItem(moreFragmentViewModel)
//        }
//        else -> {
//            Column {
//                CalendarTopText()
//                Spacer(modifier = Modifier.height(4.dp))
//            }
//        }
//    }
//}

@Composable
private fun CalendarTopText(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.ic_calendar_more),
            contentDescription = stringResource(R.string.ic_calendar_description),
            tint = primaryVariantGreenLight
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.more_fragment_calendar),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = primaryVariantGreenLight
        )
    }
}

@Composable
private fun EmptyCalendarItem(
    moreFragmentViewModel: MoreFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val isLoading = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_empty_folder),
                contentDescription = stringResource(id = R.string.no_data)
            )
            Text(text = stringResource(id = R.string.no_data))
            Spacer(modifier = Modifier.height(10.dp))
            IconButton(
                modifier = modifier.background(Color.Transparent),
                onClick = {
                    isLoading.value = !isLoading.value
                    //moreFragmentViewModel.initCalendar(GetCalendarDto("", "", "", 0, 0))
                },
                enabled = !isLoading.value
            ) {
                Icon(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = stringResource(id = R.string.reload),
                    tint = accentBlueLight
                )
            }
        }
    }
}