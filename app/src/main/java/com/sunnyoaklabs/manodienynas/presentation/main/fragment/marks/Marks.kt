package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
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
import androidx.constraintlayout.compose.ConstraintLayout
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.*
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingItem
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp.*
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MarksFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.*

@Composable
fun MarksFragment(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val marksFragmentViewModel = mainViewModel.marksFragmentViewModel
    val markFragmentTypeState = marksFragmentViewModel.markFragmentTypeState.value

    ConstraintLayout(
        Modifier.fillMaxSize()
    ) {
        val (cards, list) = createRefs()
        Column(modifier = Modifier.constrainAs(cards) {
            top.linkTo(parent.top)
        }) {
            MarksCardButton(marksFragmentViewModel)
            ControlWorkCardButton(marksFragmentViewModel)
            HomeWorkCardButton(marksFragmentViewModel)
            ClassWorkCardButton(marksFragmentViewModel)
        }
        Box(modifier = Modifier.constrainAs(list) {
            top.linkTo(cards.bottom)
        }) {
            AnimatedVisibility(visible = markFragmentTypeState.markTypeIsSelected) {
                MarksCard(marksFragmentViewModel = marksFragmentViewModel)
            }
            AnimatedVisibility(visible = markFragmentTypeState.controlWorkTypeIsSelected) {
                ControlWorkCard(marksFragmentViewModel = marksFragmentViewModel)
            }
            AnimatedVisibility(visible = markFragmentTypeState.homeWorkTypeIsSelected) {
                HomeWorkCard(marksFragmentViewModel = marksFragmentViewModel)
            }
            AnimatedVisibility(visible = markFragmentTypeState.classWorkTypeIsSelected) {
                ClassWorkCard(marksFragmentViewModel = marksFragmentViewModel)
            }
        }
    }
}