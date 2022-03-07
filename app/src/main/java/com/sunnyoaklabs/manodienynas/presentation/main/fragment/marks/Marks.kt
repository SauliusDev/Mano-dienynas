package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.*
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingItem
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp.ClassWorkCard
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp.ControlWorkCard
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp.HomeWorkCard
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp.MarksCard
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MarksFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.*

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
        MarksCard(marksFragmentViewModel = marksFragmentViewModel, Modifier.weight(1f))
        ControlWorkCard(marksFragmentViewModel = marksFragmentViewModel, Modifier.weight(0.1f))
        HomeWorkCard(marksFragmentViewModel = marksFragmentViewModel, Modifier.weight(0.1f))
        ClassWorkCard(marksFragmentViewModel = marksFragmentViewModel, Modifier.weight(0.1f))
    }
}