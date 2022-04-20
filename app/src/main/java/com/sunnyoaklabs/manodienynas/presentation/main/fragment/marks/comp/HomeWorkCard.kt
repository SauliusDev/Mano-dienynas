package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.Fragments
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MARKS_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MARKS_FRAGMENT_HOME_WORK
import com.sunnyoaklabs.manodienynas.domain.model.HomeWork
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingList
import com.sunnyoaklabs.manodienynas.presentation.core.disableScrolling
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MarksFragmentViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.SettingsMainFragmentViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.state.ClassWorkState
import com.sunnyoaklabs.manodienynas.presentation.main.state.HomeWorkState
import com.sunnyoaklabs.manodienynas.ui.theme.accentBlue
import com.sunnyoaklabs.manodienynas.ui.theme.accentBlueLight

@Composable
fun HomeWorkCard(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val marksFragmentViewModel = mainViewModel.marksFragmentViewModel
    val homeWorkState = marksFragmentViewModel.homeWorkState.value

    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    state.disableScrolling(scope)
    when {
        marksFragmentViewModel.validator.validateIsLoading(
            homeWorkState.isLoading,
            homeWorkState.isLoadingLocale,
            homeWorkState.homeWork
        ) -> {
            LoadingList(10, state)
        }
        marksFragmentViewModel.validator.validateIsEmpty(
            homeWorkState.isLoading,
            homeWorkState.isLoadingLocale,
            homeWorkState.homeWork
        ) -> {
            EmptyHomeWorkItem(mainViewModel)
        }
        else -> {
            HomeWorkMainLayout(mainViewModel, homeWorkState)
        }
    }
}

@Composable
private fun HomeWorkMainLayout(
    mainViewModel: MainViewModel,
    homeWorkState: HomeWorkState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp, top = 4.dp)
    ) {
        HomeWorkTopWidgets()
        Spacer(modifier = Modifier.height(4.dp))
        HomeWorkListLazyColumn(mainViewModel, homeWorkState)
    }
}

@Composable
fun HomeWorkTopWidgets() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.ic_homework),
            contentDescription = stringResource(R.string.marks_fragment_homework),
            tint = accentBlue
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.marks_fragment_homework),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = accentBlue
        )
    }
}

@Composable
fun HomeWorkListLazyColumn(
    mainViewModel: MainViewModel,
    homeWorkState: HomeWorkState
) {
    val lastIndex = homeWorkState.homeWork.lastIndex
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(homeWorkState.homeWork) { i, homework ->
            if (lastIndex == i) {
                mainViewModel.initPagingDataFromFragment(
                    MARKS_FRAGMENT,
                    MARKS_FRAGMENT_HOME_WORK
                )
            }
            HomeWorkItem(homework)
        }
    }
}

@Composable
private fun HomeWorkItem(
    homeWork: HomeWork,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(
                vertical = 2.dp,
                horizontal = 2.dp
            )
            .fillMaxWidth(),
        elevation = 2.dp,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = homeWork.lesson, color = accentBlue)
                    Text(text = homeWork.teacher, fontSize = 12.sp)
                }
                Text(text = homeWork.dateAddition, fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .background(Color.Gray)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${stringResource(id = R.string.due_to)} ${homeWork.dueDate}",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = homeWork.description,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
            )
        }
    }
}

@Composable
private fun EmptyHomeWorkItem(
    mainViewModel: MainViewModel,
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
                    mainViewModel.initDataOnEmptyFragment(MARKS_FRAGMENT, MARKS_FRAGMENT_HOME_WORK)
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