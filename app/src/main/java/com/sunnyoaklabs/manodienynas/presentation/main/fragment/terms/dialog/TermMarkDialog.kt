package com.sunnyoaklabs.manodienynas.presentation.main.fragment.terms.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sunnyoaklabs.manodienynas.domain.model.MarksEventItem
import com.sunnyoaklabs.manodienynas.domain.model.TermMarkDialogItem
import com.sunnyoaklabs.manodienynas.presentation.core.CustomDialogCancelButton

@Composable
fun TermMarkDialog(
    showDialog: Boolean,
    termMarkDialogItem: TermMarkDialogItem,
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
                    Column(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(text = "Įrašė įvertinimą: ${termMarkDialogItem.writer}")
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "Įrašymo data: ${termMarkDialogItem.date}")
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "Įvertinimas: ${termMarkDialogItem.mark}")
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "Kursas: ${termMarkDialogItem.course}")
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "Pastabos: ${termMarkDialogItem.remarks}")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    CustomDialogCancelButton(onNegativeClick)
                }
            }
        }
    }
}