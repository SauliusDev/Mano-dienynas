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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sunnyoaklabs.manodienynas.domain.model.MarksEventItem
import com.sunnyoaklabs.manodienynas.presentation.core.CustomDialogCancelButton

@Composable
fun AbbreviationDescriptionDialog(
    showDialog: Boolean,
    abbreviationDescriptionDialogItem: AbbreviationDescriptionDialogItem,
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
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = abbreviationDescriptionDialogItem.title.first,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = abbreviationDescriptionDialogItem.title.second,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(abbreviationDescriptionDialogItem.items) {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = it.first,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = it.second,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    CustomDialogCancelButton(onNegativeClick)
                }
            }
        }
    }
}

data class AbbreviationDescriptionDialogItem(
    val title: Pair<String, String>,
    val items: List<Pair<String, String>>,
)