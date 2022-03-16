package com.sunnyoaklabs.manodienynas.presentation.main.fragment.settings.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.presentation.core.CustomBottomNegativeButton
import com.sunnyoaklabs.manodienynas.presentation.core.CustomBottomPositiveButton
import com.sunnyoaklabs.manodienynas.presentation.core.CustomDialogCancelButton
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.SettingsMainFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.primaryGreenAccent

@Composable
fun DialogLogout(
    showDialog: Boolean,
    title: String,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Column(
                        Modifier
                            .padding(6.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = Center
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(Color.Gray))
                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                        CustomBottomNegativeButton(
                            onNegativeClick = { onNegativeClick() }, Modifier.weight(1f)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(1.dp)
                                .background(Color.Gray)
                                .height(40.dp)
                        )
                        CustomBottomPositiveButton(
                            onPositiveClick = { onPositiveClick() }, Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}