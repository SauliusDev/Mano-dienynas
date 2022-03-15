package com.sunnyoaklabs.manodienynas.presentation.main.fragment.messages.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MessagesFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.*

@Composable
fun MessagesGottenCardButton(
    messagesFragmentViewModel: MessagesFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val markFragmentTypeState = messagesFragmentViewModel.messagesFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = if (markFragmentTypeState.gottenIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            messagesFragmentViewModel.updateMessagesGottenFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_messages_gotten),
                contentDescription = stringResource(R.string.ic_mark_description),
                tint = accentGreenDark
            )
        }
    }
}

@Composable
fun MessagesSentCardButton(
    messagesFragmentViewModel: MessagesFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val markFragmentTypeState = messagesFragmentViewModel.messagesFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = if (markFragmentTypeState.sentIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            messagesFragmentViewModel.updateMessagesSentFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_messages_sent),
                contentDescription = stringResource(R.string.ic_mark_description),
                tint = accentBlue
            )
        }
    }
}

@Composable
fun MessagesStarredCardButton(
    messagesFragmentViewModel: MessagesFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val markFragmentTypeState = messagesFragmentViewModel.messagesFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = if (markFragmentTypeState.starredIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            messagesFragmentViewModel.updateMessagesStarredFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_messages_starred),
                contentDescription = stringResource(R.string.ic_mark_description),
                tint = accentYellowDark
            )
        }
    }
}

@Composable
fun MessagesDeletedCardButton(
    messagesFragmentViewModel: MessagesFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val markFragmentTypeState = messagesFragmentViewModel.messagesFragmentTypeState.value
    Button(
        modifier = modifier,
        colors = if (markFragmentTypeState.deletedIsSelected) {
            ButtonDefaults.buttonColors(accentGreenLightest)
        } else {
            ButtonDefaults.buttonColors(accentGrey)
        },
        onClick = {
            messagesFragmentViewModel.updateMessagesDeletedFragmentTypeState()
        },
        elevation = ButtonDefaults.elevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_messages_deleted),
                contentDescription = stringResource(R.string.ic_mark_description),
                tint = Color.Gray
            )
        }
    }
}