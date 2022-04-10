package com.sunnyoaklabs.manodienynas.presentation.main.fragment.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.UIEventTypes.ATTENDANCE_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.UIEventTypes.CHANGED_MARK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.UIEventTypes.CONTROL_WORK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.UIEventTypes.HOMEWORK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.UIEventTypes.MARK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingList
import com.sunnyoaklabs.manodienynas.presentation.core.disableScrolling
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.EventsFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.*

@Composable
fun EventsFragment(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val eventsFragmentViewModel = mainViewModel.eventsFragmentViewModel
    val eventState = eventsFragmentViewModel.eventState.value
    val events = eventsFragmentViewModel.eventState.value.events

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Center,
    ) {
        val scope = rememberCoroutineScope()
        val state = rememberLazyListState()
        state.disableScrolling(scope)
        when {
            eventsFragmentViewModel.validator.validateIsLoading(
                eventState.isLoading,
                eventState.isLoadingLocale,
                events
            ) -> {
                LoadingList(items = 10, state = state)
            }
            eventsFragmentViewModel.validator.validateIsEmpty(
                eventState.isLoading,
                eventState.isLoadingLocale,
                events
            ) -> {
                EmptyEventsItem(eventsFragmentViewModel)
            }
            else -> {
                EventsListLazyColumn(
                    events,
                    eventsFragmentViewModel
                )
            }
        }
    }
}

@Composable
private fun EventsListLazyColumn(
    events: List<Event>,
    eventsFragmentViewModel: EventsFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val lastIndex = events.lastIndex
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(events) { i, event ->
            if (lastIndex == i) {
                if (!eventsFragmentViewModel.eventState.value.isEveryEventLoaded) {
                    eventsFragmentViewModel.loadMoreEvents()
                }
            }
            EventCard(event = event)
        }
    }
}

@Composable
private fun EmptyEventsItem(
    eventsFragmentViewModel: EventsFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val isLoading = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Center,
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
                    eventsFragmentViewModel.initEventsAndPerson()
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
private fun EventCard(
    event: Event,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(
            vertical = 4.dp,
            horizontal = 4.dp
        ),
        elevation = 10.dp
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 2.dp,
                            horizontal = 10.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(2.5f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        SetIcon(event = event)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            if (event.title.isNotBlank()) {
                                Text(text = event.title, fontWeight = FontWeight.Bold)
                            }
                            if (event.pupilInfo.isNotBlank()) {
                                Text(text = event.pupilInfo, fontSize = 12.sp)
                            }
                            if (event.createDateText.isNotBlank()) {
                                Text(text = event.createDateText, fontSize = 12.sp)
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(10.dp)
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = event.createDate,
                        maxLines = 1,
                        textAlign = TextAlign.End
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth()
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray),
                )
                Column(
                    modifier = Modifier.padding(
                        vertical = 2.dp,
                        horizontal = 10.dp
                    )
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = event.eventHeader,
                        color = getEventColor(event),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                            .fillMaxWidth()
                    )
                    val textFontSize = if (event.title == MARK_EVENT_TYPE) 20.sp
                    else 12.sp
                    val textFontWeight = if (event.title == MARK_EVENT_TYPE) FontWeight.Bold
                    else FontWeight.Normal
                    SelectionContainer() {
                        Text(
                            text = AnnotatedString(event.eventText),
                            fontSize = textFontSize,
                            fontWeight = textFontWeight
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

private fun getEventColor(event: Event): Color {
    return when (event.title) {
        CONTROL_WORK_EVENT_TYPE -> {
            accentYellowDark
        }
        HOMEWORK_EVENT_TYPE -> {
            accentBlueLight
        }
        MARK_EVENT_TYPE -> {
            accentRedDark
        }
        ATTENDANCE_EVENT_TYPE -> {
            accentRed
        }
        CHANGED_MARK_EVENT_TYPE -> {
            accentPurpleLight
        }
        else -> {
            Color.Gray
        }
    }
}

@Composable
private fun SetIcon(event: Event) {
    when (event.title) {
        CONTROL_WORK_EVENT_TYPE -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_event_control_work),
                contentDescription = stringResource(id = R.string.event_control_work),
                tint = accentYellowDark,
                modifier = Modifier.size(30.dp)
            )
        }
        HOMEWORK_EVENT_TYPE -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_event_home_work),
                contentDescription = stringResource(id = R.string.event_home_work),
                tint = accentBlueLight,
                modifier = Modifier.size(30.dp)
            )
        }
        MARK_EVENT_TYPE -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_event_mark),
                contentDescription = stringResource(id = R.string.event_mark),
                tint = accentRedDark,
                modifier = Modifier.size(30.dp)
            )
        }
        ATTENDANCE_EVENT_TYPE -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_event_attendance),
                contentDescription = stringResource(id = R.string.event_attendance),
                tint = accentRed,
                modifier = Modifier.size(30.dp)
            )
        }
        CHANGED_MARK_EVENT_TYPE -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_mark_changed),
                contentDescription = stringResource(id = R.string.event_mark_changed),
                tint = accentPurpleLight,
                modifier = Modifier.size(30.dp)
            )
        }
        else -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_code),
                contentDescription = stringResource(id = R.string.event_unknown),
                tint = Color.Gray,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}