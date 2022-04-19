package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp.*

@Composable
fun MarksFragment(
    mainViewModel: MainViewModel,
    fragmentManager: FragmentManager,
    modifier: Modifier = Modifier
) {
    val marksFragmentViewModel = mainViewModel.marksFragmentViewModel
    val markFragmentTypeState = marksFragmentViewModel.markFragmentTypeState.value
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ConstraintLayout {
            val (cards) = createRefs()
            Column(modifier = Modifier.constrainAs(cards) {
                top.linkTo(parent.top)
            }) {
                ChangeDateButton(mainViewModel, fragmentManager)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MarksCardButton(marksFragmentViewModel, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(4.dp))
                    ControlWorkCardButton(marksFragmentViewModel, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(4.dp))
                    HomeWorkCardButton(marksFragmentViewModel, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(4.dp))
                    ClassWorkCardButton(marksFragmentViewModel, Modifier.weight(1f))
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = markFragmentTypeState.markTypeIsSelected) {
                MarksCard(mainViewModel = mainViewModel)
            }
            AnimatedVisibility(visible = markFragmentTypeState.controlWorkTypeIsSelected) {
                ControlWorkCard(mainViewModel = mainViewModel)
            }
            AnimatedVisibility(visible = markFragmentTypeState.homeWorkTypeIsSelected) {
                HomeWorkCard(mainViewModel = mainViewModel)
            }
            AnimatedVisibility(visible = markFragmentTypeState.classWorkTypeIsSelected) {
                ClassWorkCard(mainViewModel = mainViewModel)
            }
        }
    }
}