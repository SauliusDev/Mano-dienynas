package com.sunnyoaklabs.manodienynas.presentation.login.fragment.settings.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
                        Text(description)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    CustomDialogCancelButton(onNegativeClick)
                }
            }
        }
    }
}