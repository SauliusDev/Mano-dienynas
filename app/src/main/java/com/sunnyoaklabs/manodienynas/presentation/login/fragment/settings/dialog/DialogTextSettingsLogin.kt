package com.sunnyoaklabs.manodienynas.presentation.login.fragment.settings.dialog

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sunnyoaklabs.manodienynas.presentation.core.CustomDialogCancelButton

@Composable
fun DialogTextSettingsLogin(
    showDialog: Boolean,
    title: String,
    description: String,
    onNegativeClick: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Column(Modifier.padding(6.dp)) {
                        Text(title, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyColumn(Modifier.heightIn(0.dp, 300.dp)) {
                            item {
                                SelectionContainer {
                                    Text(description)
                                }
                            }
                        }
                    }
                    CustomDialogCancelButton(onNegativeClick)
                }
            }
        }
    }
}