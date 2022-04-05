package com.sunnyoaklabs.manodienynas.presentation.main.fragment.more.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.data.remote.dto.GetCalendarDto
import com.sunnyoaklabs.manodienynas.domain.model.Holiday
import com.sunnyoaklabs.manodienynas.domain.model.ParentMeeting
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingList
import com.sunnyoaklabs.manodienynas.presentation.core.disableScrolling
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MoreFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.accentBlueLight
import com.sunnyoaklabs.manodienynas.ui.theme.accentGreenLightest
import com.sunnyoaklabs.manodienynas.ui.theme.accentYellowDark
import com.sunnyoaklabs.manodienynas.ui.theme.primaryVariantGreenLight

@Composable
fun ParentMeetingsCard(
    moreFragmentViewModel: MoreFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val parentMeetingState = moreFragmentViewModel.parentMeetingState.value

    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    state.disableScrolling(scope)
    when {
        moreFragmentViewModel.validator.validateIsLoading(
            parentMeetingState.isLoading,
            parentMeetingState.isLoadingLocale,
            parentMeetingState.parentMeetings
        ) -> {
            LoadingList(10, state)
        }
        moreFragmentViewModel.validator.validateIsEmpty(
            parentMeetingState.isLoading,
            parentMeetingState.isLoadingLocale,
            parentMeetingState.parentMeetings
        ) -> {
            EmptyParentMeetingsItem(moreFragmentViewModel)
        }
        else -> {
            Column {
                ParentMeetingsTopText()
                Spacer(modifier = Modifier.height(4.dp))
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(parentMeetingState.parentMeetings) {
                        ParentMeetingsItem(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun ParentMeetingsTopText(
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
            painter = painterResource(id = R.drawable.ic_parent_meetings),
            contentDescription = stringResource(R.string.ic_parent_meetings_description),
            tint = accentBlueLight
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.more_fragment_parent_meetings),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = accentBlueLight
        )
    }
}

@Composable
private fun ParentMeetingsItem(
    parentMeeting: ParentMeeting,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(
                4.dp
            )
            .fillMaxWidth(),
        elevation = 2.dp,
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(accentBlueLight),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = parentMeeting.className)
                    Text(text = parentMeeting.creationDate)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_date),
                    contentDescription = stringResource(id = R.string.ic_date_description),
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = parentMeeting.date, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = stringResource(id = R.string.ic_location_description),
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = parentMeeting.location)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = parentMeeting.description, Modifier.padding(4.dp))
        }
    }
}

@Composable
private fun EmptyParentMeetingsItem(
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
                    moreFragmentViewModel.initParentMeetings()
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