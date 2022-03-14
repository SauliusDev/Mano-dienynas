package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.*
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingItem
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.events.disableScrolling
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MarksFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.*
import kotlinx.coroutines.flow.collect

@Composable
fun MarksCard(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val marksEventItem = remember {
        mutableStateOf(
            MarksEventItem(
                "",
                "",
                "",
                "",
                ""
            )
        )
    }
    val marksState = marksFragmentViewModel.markState.value
    val attendanceState = marksFragmentViewModel.attendanceState.value
    val collapsableSectionMarks = mutableListOf<CollapsableSectionMarks>()
    LaunchedEffect(key1 = true) {
        marksFragmentViewModel.marksEventItemFlow.collect {
            // TODO
            //  -show some sort loading animation
            //  -show dialog when !isLoading
            it.marksEventItem?.let { marksEventItemIt ->
                marksEventItem.value = marksEventItemIt
            }
            setShowDialog(true)
        }
    }
    for (i in marksState.marks.indices) {
        val attendance = attendanceState.attendance.find { attendance ->
            attendance.teacher == marksState.marks[i].teacher
        }
        if (i == marksState.marks.size - 1) {
            collapsableSectionMarks.add(
                CollapsableSectionMarks(
                    Mark("", "", "", listOf(), 0),
                    attendanceState.attendance[attendanceState.attendance.lastIndex]
                )
            )
        } else {
            collapsableSectionMarks.add(
                CollapsableSectionMarks(
                    marksState.marks[i],
                    attendance ?: Attendance("", "", listOf(), listOf(), null)
                )
            )
        }
    }
    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    state.disableScrolling(scope)
    EventDialog(
        showDialog,
        marksEventItem.value,
        setShowDialog
    )
    when {
        marksState.isLoading || attendanceState.isLoading -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = state
            ) {
                items(10) {
                    LoadingItem(1f / (it + 1))
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
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = accentRed
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                CollapsableLazyColumnMarks(
                    marksFragmentViewModel = marksFragmentViewModel,
                    sections = collapsableSectionMarks
                )
            }
        }
    }
}

@Composable
fun CollapsableLazyColumnMarks(
    marksFragmentViewModel: MarksFragmentViewModel,
    sections: List<CollapsableSectionMarks>,
    modifier: Modifier = Modifier
) {
    val collapsedState = remember(sections) { sections.map { true }.toMutableStateList() }
    LazyColumn(modifier) {
        sections.forEachIndexed { i, dataItem ->
            val collapsed = collapsedState[i]
            if (dataItem.attendance.lessonTitle == "Viso") {
                item {
                    AttendanceAllItem(dataItem)
                }
            } else {
                item {
                    MarksItem(marksFragmentViewModel, i, dataItem, collapsedState, collapsed)
                }
            }
        }
    }
}

@Composable
fun MarksItem(
    marksFragmentViewModel: MarksFragmentViewModel,
    i: Int,
    dataItem: CollapsableSectionMarks,
    collapsedState: SnapshotStateList<Boolean>,
    collapsed: Boolean,
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
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    collapsedState[i] = !collapsed
                }
                .fillMaxWidth()
                .background(
                    colorResource(
                        id = if (collapsed) {
                            android.R.color.transparent
                        } else {
                            R.color.accent_grey
                        }
                    )
                )
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
                        Text(
                            text = dataItem.mark.teacher,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
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
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    marksFragmentViewModel,
                    it
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                        dataItem.attendance.attendance.subList(5 * it, 5 * it + 5),
                        dataItem.attendance.attendanceRange[it + 1]
                    )
                } else {
                    AttendanceEventItem(
                        dataItem.attendance.attendance.subList(5 * it, 5 * it + 5),
                        dataItem.attendance.attendanceRange[it]
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}

@Composable
fun AttendanceAllItem(
    collapsableSectionMarks: CollapsableSectionMarks,
    modifier: Modifier = Modifier
) {
    val collapsedState = remember(collapsableSectionMarks) { mutableStateOf(true) }
    Card(
        modifier = modifier
            .padding(
                vertical = 2.dp,
                horizontal = 2.dp
            ),
        elevation = 2.dp,
    ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    collapsedState.value = !collapsedState.value
                }
                .fillMaxWidth()
                .background(
                    colorResource(
                        id = if (collapsedState.value) {
                            android.R.color.transparent
                        } else {
                            R.color.accent_grey
                        }
                    )
                )
        ) {
            Box {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_all_attendance),
                        contentDescription = stringResource(id = R.string.go_to_settings_screen),
                        tint = accentPurple,
                        modifier = Modifier
                            .padding(10.dp)
                            .size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(id = R.string.all_lesson_attendance),
                        fontSize = 16.sp
                    )
                }
            }
            Icon(
                Icons.Default.run {
                    if (collapsedState.value)
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
    if (!collapsedState.value) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.marks_fragment_item_attendance))
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .weight(1f)
                    .background(Color.Gray)
            )
        }
        LazyRow {
            val termCount =
                if (collapsableSectionMarks.attendance.attendance.size / 15 == 1) 3
                else 4
            items(termCount) {
                if (termCount == 3 && it == 2) {
                    AttendanceEventItem(
                        collapsableSectionMarks.attendance.attendance.subList(
                            5 * it,
                            5 * it + 5
                        ),
                        collapsableSectionMarks.attendance.attendanceRange[it + 1]
                    )
                } else {
                    AttendanceEventItem(
                        collapsableSectionMarks.attendance.attendance.subList(
                            5 * it,
                            5 * it + 5
                        ),
                        collapsableSectionMarks.attendance.attendanceRange[it]
                    )
                }
            }
        }
    }
}

@Composable
fun MarkEventItem(
    marksFragmentViewModel: MarksFragmentViewModel,
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
                marksFragmentViewModel.initMarksEventItem(markEvent.infoUrl)
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

@Composable
fun EventDialog(
    showDialog: Boolean,
    marksEventItem: MarksEventItem,
    setShowDialog: (Boolean) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.padding(0.dp),
            onDismissRequest = {
            },
            confirmButton = {},
            dismissButton = {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(primaryGreenAccent),
                    onClick = {
                        setShowDialog(false)
                    },
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
            },
            title = {
                Text(
                    marksEventItem.writer + "\n" +
                        marksEventItem.date + "\n" +
                        marksEventItem.evaluation + "\n" +
                        marksEventItem.type + "\n" +
                        marksEventItem.lesson
                )
            },
        )
    }
}