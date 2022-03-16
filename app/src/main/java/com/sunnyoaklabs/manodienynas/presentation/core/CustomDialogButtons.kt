package com.sunnyoaklabs.manodienynas.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.ui.theme.primaryGreenAccent

@Composable
fun CustomDialogCancelButton(
    onNegativeClick: () -> Unit,
) {
    Spacer(modifier = Modifier
        .height(0.5.dp)
        .fillMaxWidth()
        .background(Color.Gray))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable {
                onNegativeClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.cancel), fontWeight = FontWeight.Bold, color = primaryGreenAccent)
    }
}

@Composable
fun CustomBottomPositiveButton(
    onPositiveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(40.dp)
            .clickable {
                onPositiveClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.ok), fontWeight = FontWeight.Bold, color = primaryGreenAccent)
    }
}

@Composable
fun CustomBottomNegativeButton(
    onNegativeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(40.dp)
            .clickable {
                onNegativeClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.cancel), fontWeight = FontWeight.Bold, color = primaryGreenAccent)
    }
}