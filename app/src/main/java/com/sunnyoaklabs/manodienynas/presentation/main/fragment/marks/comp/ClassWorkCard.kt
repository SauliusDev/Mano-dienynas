package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MARKS_FRAGMENT_CLASS_WORK
import com.sunnyoaklabs.manodienynas.domain.model.ClassWork
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingList
import com.sunnyoaklabs.manodienynas.presentation.core.disableScrolling
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MarksFragmentViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.state.ClassWorkState
import com.sunnyoaklabs.manodienynas.ui.theme.accentBlue
import com.sunnyoaklabs.manodienynas.ui.theme.accentBlueLight

@Composable
fun ClassWorkCard(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val marksFragmentViewModel = mainViewModel.marksFragmentViewModel
    val classWorkState = marksFragmentViewModel.classWorkState.value

    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    state.disableScrolling(scope)
    when {
        marksFragmentViewModel.validator.validateIsLoading(
            classWorkState.isLoading,
            classWorkState.isLoadingLocale,
            classWorkState.classWork
        ) -> {
            LoadingList(10, state)
        }
        marksFragmentViewModel.validator.validateIsEmpty(
            classWorkState.isLoading,
            classWorkState.isLoadingLocale,
            classWorkState.classWork
        ) -> {
            EmptyClassWorkItem(mainViewModel)
        }
        else -> {
            ClassWorkMainLayout(mainViewModel, classWorkState)
        }
    }
}

@Composable
private fun ClassWorkMainLayout(
    mainViewModel: MainViewModel,
    classWorkState: ClassWorkState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        ClassWorkTopWidgets()
        Spacer(modifier = Modifier.height(4.dp))
        ClassWorkListLazyColumn(mainViewModel, classWorkState)
    }
}

@Composable
private fun ClassWorkTopWidgets() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.ic_class_work),
            contentDescription = stringResource(R.string.marks_fragment_class_work),
            tint = accentBlue
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.marks_fragment_class_work),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = accentBlue
        )
    }
}

@Composable
private fun ClassWorkListLazyColumn(
    mainViewModel: MainViewModel,
    classWorkState: ClassWorkState
) {
    val lastIndex = classWorkState.classWork.lastIndex
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(classWorkState.classWork) { i, classwork ->
            if (lastIndex == i) {
                mainViewModel.initPagingDataFromFragment(
                    MARKS_FRAGMENT,
                    MARKS_FRAGMENT_CLASS_WORK
                )
            }
            ClassWorkItem(classwork)
        }
    }
}

@Composable
private fun ClassWorkItem(
    classWork: ClassWork,
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
                    Text(text = classWork.lesson, color = accentBlue)
                    Text(text = classWork.teacher, fontSize = 12.sp)
                }
                Text(text = classWork.dateAddition, fontSize = 12.sp, color = Color.Gray)
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
                text = classWork.description,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
            )
        }
    }
}

@Composable
private fun EmptyClassWorkItem(
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
                    mainViewModel.initDataOnEmptyFragment(MARKS_FRAGMENT, MARKS_FRAGMENT_CLASS_WORK)
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