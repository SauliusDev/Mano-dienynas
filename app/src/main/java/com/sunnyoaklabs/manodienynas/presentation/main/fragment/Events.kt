package com.sunnyoaklabs.manodienynas.presentation.main.fragment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.EventTypes
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.ATTENDANCE_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.CONTROL_WORK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.HOMEWORK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.MARK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.EventsFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.custom.LocalSpacing
import com.sunnyoaklabs.manodienynas.ui.theme.*
import kotlinx.coroutines.flow.collect

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@Composable
fun EventsFragment(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val eventsFragmentViewModel = mainViewModel.eventsFragmentViewModel
    val events = mainViewModel.eventsFragmentViewModel.eventState.value.events
    val terms = mainViewModel.eventsFragmentViewModel.termState.value.terms

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Center,
    ) {
        val scrollState = rememberLazyListState()
        val lastIndex = events.lastIndex
        // observer when reached end of list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(events) { i, event  ->
                if (lastIndex == i) {
                    if (!eventsFragmentViewModel.eventState.value.isEveryEventLoaded) {
                        eventsFragmentViewModel.loadMoreEvents()
                    }
                }
                EventCard(event = event)
            }
        }
    }
}

@Composable
fun TermsCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(
            top = LocalSpacing.current.medium,
            start = LocalSpacing.current.small,
            end = LocalSpacing.current.small
        ),
        elevation = 10.dp
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {

        }
    }
}

@Composable
fun EventCard(
    event: Event,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(
            vertical = 4.dp,
            horizontal = 8.dp
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
                Spacer(modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth())
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
                        setIcon(event = event)
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
                        Spacer(modifier = Modifier
                            .fillMaxHeight()
                            .width(10.dp))
                    }
                    Text(modifier = Modifier.weight(1f), text = event.createDate, maxLines = 1, textAlign = TextAlign.End)
                }
                Spacer(modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth())
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray),
                )
                Column(modifier = Modifier.padding(
                        vertical = 2.dp,
                        horizontal = 10.dp
                    )
                ) {
                    Spacer(modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth())
                    Text(
                        text = event.eventHeader,
                        color = getEventColor(event),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth())
                    val textFontSize =  if (event.title == MARK_EVENT_TYPE) 20.sp
                    else 12.sp
                    val textFontWeight =  if (event.title == MARK_EVENT_TYPE) FontWeight.Bold
                    else FontWeight.Normal
                    SelectionContainer() {
                        Text(
                            text = AnnotatedString(event.eventText),
                            fontSize = textFontSize,
                            fontWeight = textFontWeight
                        )
                    }
                    Spacer(modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth())
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
        else -> {
            Color.Gray
        }
    }
}

@Composable
private fun setIcon(event: Event) {
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