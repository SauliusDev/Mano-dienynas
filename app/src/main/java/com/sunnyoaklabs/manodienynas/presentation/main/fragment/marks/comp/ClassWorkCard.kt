package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.sunnyoaklabs.manodienynas.domain.model.ClassWork
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingItem
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.disableScrolling
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MarksFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.accentBlue
import com.sunnyoaklabs.manodienynas.ui.theme.accentBlueLight

@Composable
fun ClassWorkCard(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val classWorkState = marksFragmentViewModel.classWorkState.value
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
            classWorkState.isLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = state
                ) {
                    items(10) {
                        LoadingItem(1f / (it + 1))
                    }
                }
            }
            classWorkState.classWork.isEmpty() -> {
                EmptyClassWorkItem(marksFragmentViewModel)
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
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(classWorkState.classWork) {
                            ClassWorkItem(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClassWorkItem(
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
fun EmptyClassWorkItem(
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
                    marksFragmentViewModel.initClassWork()
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