package com.sunnyoaklabs.manodienynas.presentation.main.fragment

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.EventsFragmentViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MarksFragmentViewModel
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
        Text(text = "Marks fragment", color = Color.Black)
        MarksCard(marksFragmentViewModel = marksFragmentViewModel)
        ControlWorkCard(marksFragmentViewModel = marksFragmentViewModel)
        HomeWorkCard(marksFragmentViewModel = marksFragmentViewModel)
        ClassWorkCard(marksFragmentViewModel = marksFragmentViewModel)
    }
}

@Composable
fun MarksCard(
    marksFragmentViewModel: MarksFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val marks = marksFragmentViewModel.markState.value.marks
    val attendance = marksFragmentViewModel.attendanceState.value.attendance

    Card(
        modifier = modifier
            .padding(
                vertical = 4.dp,
                horizontal = 4.dp
            ),
        elevation = 5.dp,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.marks_fragment_marks),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyColumn(

            ) {
                items(marks) {

                }
            }
        }
    }
}

@Composable
fun MarkItem() {

}

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