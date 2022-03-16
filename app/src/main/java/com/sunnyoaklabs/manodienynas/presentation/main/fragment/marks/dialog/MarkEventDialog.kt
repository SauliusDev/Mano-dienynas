package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sunnyoaklabs.manodienynas.domain.model.MarksEventItem
import com.sunnyoaklabs.manodienynas.presentation.core.CustomDialogCancelButton

@Composable
fun MarkEventDialog(
    showDialog: Boolean,
    marksEventItem: MarksEventItem,
    onNegativeClick: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column() {
                    Column(Modifier.padding(6.dp)) {
                        Text(
                            marksEventItem.writer + "\n" +
                                    marksEventItem.date + "\n" +
                                    marksEventItem.evaluation + "\n" +
                                    marksEventItem.type + "\n" +
                                    marksEventItem.lesson
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    CustomDialogCancelButton(onNegativeClick)
                }
            }
        }
    }
}