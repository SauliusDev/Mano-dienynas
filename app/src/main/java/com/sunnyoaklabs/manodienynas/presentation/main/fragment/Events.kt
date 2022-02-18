package com.sunnyoaklabs.manodienynas.presentation.main.fragment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.EventsFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.custom.LocalSpacing
import kotlinx.coroutines.flow.collect

@Composable
fun EventsFragment(
    mainViewModel: MainViewModel
) {
    val events = mainViewModel.eventsFragmentViewModel.eventState.value.events
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(events) {
                Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                Text(text = it.eventHeader + it.eventText, Modifier.padding(horizontal = 10.dp))
                Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
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