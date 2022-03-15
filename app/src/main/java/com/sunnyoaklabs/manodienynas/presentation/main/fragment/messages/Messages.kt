package com.sunnyoaklabs.manodienynas.presentation.main.fragment.messages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.comp.*
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.messages.comp.*
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.EventsFragmentViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MessagesFragmentViewModel

@Composable
fun MessagesFragment(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val messagesFragmentViewModel = mainViewModel.messagesFragmentViewModel
    val messagesFragmentTypeState = messagesFragmentViewModel.messagesFragmentTypeState.value
    Column(
        modifier = Modifier.fillMaxSize()
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
                    MessagesGottenCardButton(messagesFragmentViewModel, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(4.dp))
                    MessagesSentCardButton(messagesFragmentViewModel, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(4.dp))
                    MessagesStarredCardButton(messagesFragmentViewModel, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(4.dp))
                    MessagesDeletedCardButton(messagesFragmentViewModel, Modifier.weight(1f))
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = messagesFragmentTypeState.gottenIsSelected) {
                MessagesGottenCard(messagesFragmentViewModel = messagesFragmentViewModel)
            }
            AnimatedVisibility(visible = messagesFragmentTypeState.sentIsSelected) {
                MessagesSentCard(messagesFragmentViewModel = messagesFragmentViewModel)
            }
            AnimatedVisibility(visible = messagesFragmentTypeState.starredIsSelected) {
                MessagesStarredCard(messagesFragmentViewModel = messagesFragmentViewModel)
            }
            AnimatedVisibility(visible = messagesFragmentTypeState.deletedIsSelected) {
                MessagesDeletedCard(messagesFragmentViewModel = messagesFragmentViewModel)
            }
        }
    }
}