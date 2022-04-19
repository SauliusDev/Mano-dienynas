package com.sunnyoaklabs.manodienynas.presentation.main.fragment.more

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp.*
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.more.comp.*

@Composable
fun MoreFragment(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val moreFragmentViewModel = mainViewModel.moreFragmentViewModel
    val moreFragmentTypeState = moreFragmentViewModel.moreFragmentTypeState.value
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ConstraintLayout {
            val (cards) = createRefs()
            Column(modifier = Modifier.constrainAs(cards) {
                top.linkTo(parent.top)
            }) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ScheduleCardButton(moreFragmentViewModel, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(4.dp))
                    //CalendarCardButton(moreFragmentViewModel, Modifier.weight(1f))
                    //Spacer(modifier = Modifier.width(4.dp))
                    HolidayCardButton(moreFragmentViewModel, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(4.dp))
                    ParentMeetingsCardButton(moreFragmentViewModel, Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = moreFragmentTypeState.scheduleIsSelected) {
                ScheduleCard(mainViewModel = mainViewModel)
            }
            AnimatedVisibility(visible = moreFragmentTypeState.holidayIsSelected) {
                HolidayCard(mainViewModel = mainViewModel)
            }
            AnimatedVisibility(visible = moreFragmentTypeState.parentMeetingsIsSelected) {
                ParentMeetingsCard(mainViewModel = mainViewModel)
            }
        }
    }
}