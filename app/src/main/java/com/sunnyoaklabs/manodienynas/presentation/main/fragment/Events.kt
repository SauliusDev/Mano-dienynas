package com.sunnyoaklabs.manodienynas.presentation.main.fragment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.Event
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.EventsFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.custom.LocalSpacing
import com.sunnyoaklabs.manodienynas.ui.theme.*
import kotlinx.coroutines.flow.collect

@Composable
fun EventsFragment(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val events = mainViewModel.eventsFragmentViewModel.eventState.value.events
    val terms = mainViewModel.eventsFragmentViewModel.termState.value.terms

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Center,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(events) { event ->
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
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        setIcon(event = event)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = event.title, fontWeight = FontWeight.Bold)
                            Text(text = event.pupilInfo, fontSize = 12.sp)
                            Text(text = event.createDateText, fontSize = 12.sp)
                        }
                    }
                    Text(text = event.createDate)
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
                    Text(text = event.eventText)
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
        EventTypes.controlWork -> {
            accentYellowDark
        }
        EventTypes.homeWork -> {
            accentBlueLight
        }
        EventTypes.mark -> {
            accentRedDark
        }
        EventTypes.attendance -> {
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
        EventTypes.controlWork -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_event_control_work),
                contentDescription = stringResource(id = R.string.event_control_work),
                tint = accentYellowDark,
                modifier = Modifier.size(30.dp)
            )
        }
        EventTypes.homeWork -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_event_home_work),
                contentDescription = stringResource(id = R.string.event_home_work),
                tint = accentBlueLight,
                modifier = Modifier.size(30.dp)
            )
        }
        EventTypes.mark -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_event_mark),
                contentDescription = stringResource(id = R.string.event_mark),
                tint = accentRedDark,
                modifier = Modifier.size(30.dp)
            )
        }
        EventTypes.attendance -> {
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

object EventTypes {
    const val controlWork = "Atsiskaitymai"
    const val homeWork = "Namų darbai"
    const val mark = "Pažimys"
    const val attendance = "Lankomumas"
}