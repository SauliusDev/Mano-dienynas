package com.sunnyoaklabs.manodienynas.presentation.main.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
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
    val marks = marksFragmentViewModel.markState.value.marks
    val attendance = marksFragmentViewModel.attendanceState.value.attendance
    val classWork = marksFragmentViewModel.classWorkState.value.classWork
    val homeWork = marksFragmentViewModel.homeWorkState.value.homeWork

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Marks fragment", color = Color.Black)
        // TODO make marks fragment lmao
    }
}