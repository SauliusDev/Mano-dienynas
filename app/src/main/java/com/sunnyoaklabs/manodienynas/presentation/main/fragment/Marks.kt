package com.sunnyoaklabs.manodienynas.presentation.main.fragment

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.Attendance
import com.sunnyoaklabs.manodienynas.domain.model.AttendanceRange
import com.sunnyoaklabs.manodienynas.domain.model.Mark
import com.sunnyoaklabs.manodienynas.domain.model.MarkEvent
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingItem
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.EventsFragmentViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MarksFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.accentBlueLight
import com.sunnyoaklabs.manodienynas.ui.theme.accentRed
import com.sunnyoaklabs.manodienynas.ui.theme.accentYellowDark
import io.ktor.utils.io.*

@Composable
fun MarksFragment(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val marksFragmentViewModel = mainViewModel.marksFragmentViewModel

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MarksCard(marksFragmentViewModel = marksFragmentViewModel, Modifier.weight(1f))
        ControlWorkCard(marksFragmentViewModel = marksFragmentViewModel, Modifier.weight(0.2f))
        HomeWorkCard(marksFragmentViewModel = marksFragmentViewModel, Modifier.weight(0.4f))
        ClassWorkCard(marksFragmentViewModel = marksFragmentViewModel, Modifier.weight(0.4f))
    }
}

@Composable
fun MarksCard(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val marksState = marksFragmentViewModel.markState.value
    val attendanceState = marksFragmentViewModel.attendanceState.value
    val collapsableSectionMarks = mutableListOf<CollapsableSectionMarks>()
    for (i in marksState.marks.indices) {
        collapsableSectionMarks.add(CollapsableSectionMarks(marksState.marks[i], attendanceState.attendance[i]))
    }

    Card(
        modifier = modifier
            .padding(
                vertical = 4.dp,
                horizontal = 4.dp
            ),
        elevation = 5.dp,
    ) {
        val scope = rememberCoroutineScope()
        val state = rememberLazyListState()
        state.disableScrolling(scope)
        when {
            marksState.isLoading || attendanceState.isLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = state
                ) {
                    items(10) {
                        LoadingItem(1f/(it+1))
                    }
                }
            }
            marksState.marks.isEmpty() || attendanceState.attendance.isEmpty() -> {
                EmptyMarksItem(marksFragmentViewModel)
            }
            else -> {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 4.dp)
                ) {
                    // TODO background change if clicked
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background()) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = R.drawable.ic_mark),
                            contentDescription = stringResource(R.string.ic_mark_description),
                            tint = accentRed
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = stringResource(id = R.string.marks_fragment_marks),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = accentRed
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    CollapsableLazyColumnMarks(
                        sections = collapsableSectionMarks
                    )
                }
            }
        }
    }
}

@Composable
fun CollapsableLazyColumnMarks(
    sections: List<CollapsableSectionMarks>,
    modifier: Modifier = Modifier
) {
    val collapsedState = remember(sections) { sections.map { true }.toMutableStateList() }
    LazyColumn(modifier) {
        sections.forEachIndexed { i, dataItem ->
            val collapsed = collapsedState[i]
            item(key = "header_$i") {
                Row(
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            collapsedState[i] = !collapsed
                        }
                        .fillMaxWidth()
                ) {
                    Box {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(accentRed)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = dataItem.mark.average.ifBlank { "-" },
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier,
                                        fontSize = 14.sp,
                                    )
                                }
                            }
                            Column() {
                                Text(text = dataItem.mark.lesson)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = dataItem.mark.teacher, fontSize = 14.sp, color = Color.Gray)
                            }
                        }
                    }
                    Icon(
                        Icons.Default.run {
                            if (collapsed)
                                KeyboardArrowDown
                            else
                                KeyboardArrowUp
                        },
                        contentDescription = "",
                        tint = Color.LightGray,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
            if (!collapsed) {
                item(key = "content_$i") {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(id = R.string.marks_fragment_item_marks))
                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .weight(1f)
                                .background(Color.Gray)
                        )
                    }
                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        items(dataItem.mark.markEvent) {
                            MarkEventItem(
                                it
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(id = R.string.marks_fragment_item_attendance))
                        Spacer(
                            modifier = Modifier
                                .height(2.dp)
                                .weight(1f)
                                .background(Color.Gray)
                        )
                    }
                    LazyRow {
                        val termCount = if (dataItem.attendance.attendance.size / 15 == 1) 3
                        else 4
                        items(termCount) {
                            if (termCount == 3 && it == 2) {
                                AttendanceEventItem(
                                    dataItem.attendance.attendance.subList(5*it, 5*it+5),
                                    dataItem.attendance.attendanceRange[it + 1]
                                )
                            } else {
                                AttendanceEventItem(
                                    dataItem.attendance.attendance.subList(5*it, 5*it+5),
                                    dataItem.attendance.attendanceRange[it]
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                    )
                }
            }
        }
    }
}

@Composable
fun MarkEventItem(
    markEvent: MarkEvent,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .size(24.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(accentRed)
            .clickable {
                // TODO call viewModel to make event details request
                Log.e("console log", "mark event url: " + markEvent.infoUrl)
            },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = markEvent.marks)
        }
    }
}

@Composable
fun AttendanceEventItem(
    attendance: List<Int>,
    attendanceRange: AttendanceRange,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(
                vertical = 2.dp,
                horizontal = 2.dp
            ),
        elevation = 2.dp,
    ) {
        Column(
            modifier = Modifier.padding(2.dp)
        ) {
            Text(text = attendanceRange.title)
            Spacer(modifier = Modifier.height(1.dp))
            Text(text = attendanceRange.date ?: "", fontSize = 12.sp, color = Color.Gray)
            Column() {
                for (h in 0..4) {
                    AttendanceEventConditionItem(
                        stringArrayResource(id = R.array.attendance_category)[h],
                        attendance[h]
                    )
                }
            }
        }
    }
}

@Composable
fun AttendanceEventConditionItem(
    condition: String,
    lessons: Int,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = condition)
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = modifier
                .size(16.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(accentYellowDark)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = lessons.toString(), fontSize = 12.sp)
            }
        }
    }
}

data class CollapsableSectionMarks(val mark: Mark, val attendance: Attendance)

@Composable
fun ControlWorkCard(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {

}

@Composable
fun HomeWorkCard(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val homeWork = marksFragmentViewModel.homeWorkState.value.homeWork

}

@Composable
fun ClassWorkCard(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val classWork = marksFragmentViewModel.classWorkState.value.classWork

}

@Composable
fun CardBase(
    composable: Composable,
    modifier: Modifier = Modifier
) {

}

@Composable
fun EmptyMarksItem(
    marksFragmentViewModel: MarksFragmentViewModel,
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
                    marksFragmentViewModel.initMarks()
                    marksFragmentViewModel.initAttendance()
                    marksFragmentViewModel.initClassWork()
                    marksFragmentViewModel.initHomeWork()
                    marksFragmentViewModel.initControlWork()
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