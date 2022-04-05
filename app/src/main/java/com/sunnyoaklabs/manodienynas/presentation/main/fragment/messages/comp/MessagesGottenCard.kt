package com.sunnyoaklabs.manodienynas.presentation.main.fragment.messages.comp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.Message
import com.sunnyoaklabs.manodienynas.domain.model.MessageIndividual
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingList
import com.sunnyoaklabs.manodienynas.presentation.core.disableScrolling
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.messages.dialog.DialogMessageIndividual
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.MessagesFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.accentBlueLight
import com.sunnyoaklabs.manodienynas.ui.theme.accentGreenDark
import kotlinx.coroutines.flow.collect

@Composable
fun MessagesGottenCard(
    messagesFragmentViewModel: MessagesFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val messagesGottenState = messagesFragmentViewModel.messagesGottenState.value

    val messageIndividual = remember {
        mutableStateOf(
            MessageIndividual(
                "", "", "", "", "", "", emptyList(), 0
            )
        )
    }
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        messagesFragmentViewModel.messageIndividualFlow.collect {
            it.messageIndividual?.let { marksEventItemIt ->
                messageIndividual.value = marksEventItemIt
            }
            if (messageIndividual.value.messageId.isNotBlank()) {
                showDialog = true
            }
        }
    }

    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    state.disableScrolling(scope)
    when {
        messagesFragmentViewModel.validator.validateIsLoading(
            messagesGottenState.isLoading,
            messagesGottenState.isLoadingLocale,
            messagesGottenState.messagesGotten
        ) -> {
            LoadingList(items = 10, state = state)
        }
        messagesFragmentViewModel.validator.validateIsEmpty(
            messagesGottenState.isLoading,
            messagesGottenState.isLoadingLocale,
            messagesGottenState.messagesGotten
        ) -> {
            EmptyMessagesGottenItem(messagesFragmentViewModel)
        }
        else -> {
            val lastIndex = messagesGottenState.messagesGotten.lastIndex
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 4.dp)
            ) {
                MessagesGottenTypeText()
                Spacer(modifier = Modifier.height(4.dp))
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    itemsIndexed(messagesGottenState.messagesGotten) { i, message ->
                        if (lastIndex == i) {
                            if (!messagesGottenState.isEverythingLoaded) {
                                messagesFragmentViewModel.initMessagesGottenByCondition()
                            }
                        }
                        MessagesGottenItem(messagesFragmentViewModel, message)
                    }
                }
            }
        }
    }
    DialogMessageIndividual(
        showDialog,
        messageIndividual.value,
        onNegativeClick = {showDialog = false},
        onDismiss = {showDialog = false}
    )
}

@Composable
private fun MessagesGottenItem(
    messagesFragmentViewModel: MessagesFragmentViewModel,
    message: Message,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(
                vertical = 2.dp,
                horizontal = 2.dp
            )
            .fillMaxWidth()
            .clickable {
                messagesFragmentViewModel.initMessagesIndividual(message.messageId)
            },
        elevation = 2.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = message.date,
                color = accentGreenDark,
                modifier = Modifier.padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold
            )
            Column {
                Text(text = message.theme)
                Text(text = message.sender, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}


@Composable
private fun EmptyMessagesGottenItem(
    messagesFragmentViewModel: MessagesFragmentViewModel,
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
                    messagesFragmentViewModel.initMessagesGotten()
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

@Composable
private fun MessagesGottenTypeText() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.ic_messages_gotten),
            contentDescription = stringResource(R.string.messages_fragment_gotten),
            tint = accentGreenDark
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.messages_fragment_gotten),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = accentGreenDark
        )
    }
}
