package com.sunnyoaklabs.manodienynas.presentation.main.fragment.more.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceAround
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.ScheduleDay
import com.sunnyoaklabs.manodienynas.domain.model.ScheduleOneLesson
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingList
import com.sunnyoaklabs.manodienynas.presentation.core.disableScrolling
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MoreFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.accentBlueLight
import com.sunnyoaklabs.manodienynas.ui.theme.primaryVariantGreenLight

@Composable
fun ScheduleCard(
    moreFragmentViewModel: MoreFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val scheduleState = moreFragmentViewModel.scheduleState.value

    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    state.disableScrolling(scope)
    when {
        scheduleState.isLoading -> {
            LoadingList(10, state)
        }
        scheduleState.schedule.isEmpty() -> {
            EmptyScheduleMeetingsItem(moreFragmentViewModel)
        }
        else -> {
            Column {
                ScheduleTopText()
                Spacer(modifier = Modifier.height(4.dp))
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    items(scheduleState.schedule) {
                        if (it.dayLessons.isNotEmpty()) {
                            ScheduleItem(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScheduleItem(
    scheduleDay: ScheduleDay,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
    ) {
        Column {
            Column(
                modifier = Modifier
                    .background(accentBlueLight)
                    .fillMaxWidth()
            ) {
                Text(text = weekDays[scheduleDay.dayLessons[0].weekDay.toInt()], fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
            ) {
                scheduleDay.dayLessons.forEach {
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        horizontalArrangement = SpaceBetween,
                        verticalAlignment = CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = it.lessonOrder.toString(), modifier = Modifier.weight(0.05f))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = it.lesson, modifier = Modifier.weight(0.7f))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = it.timeRange, modifier = Modifier.weight(0.25f))
                    }
                    if (scheduleDay.dayLessons.last().lessonOrder != it.lessonOrder) {
                        Spacer(modifier = Modifier
                            .padding(top = 2.dp)
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}

private val weekDays = listOf("Pirmadienis", "Antradienis", "Trečiadienis", "Ketvirtadienis", "Penktadienis", "Šeštadienis", "Sekmadienis")

@Composable
private fun ScheduleTopText(
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
            painter = painterResource(id = R.drawable.ic_schedule),
            contentDescription = stringResource(R.string.ic_schedule_description),
            tint = primaryVariantGreenLight
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.more_fragment_schedule),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = primaryVariantGreenLight
        )
    }
}

@Composable
private fun EmptyScheduleMeetingsItem(
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
                    moreFragmentViewModel.initSchedule()
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