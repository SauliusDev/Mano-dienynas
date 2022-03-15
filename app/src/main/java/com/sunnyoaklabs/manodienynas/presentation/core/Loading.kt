package com.sunnyoaklabs.manodienynas.presentation.core

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingList(
    items: Int,
    state: LazyListState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = state
    ) {
        items(items) {
            LoadingItem(1f / (it + 1))
        }
    }
}

@Composable
private fun LoadingItem(
    transparency: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(
                vertical = 4.dp,
                horizontal = 4.dp
            )
            .alpha(transparency),
        elevation = 5.dp,
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            Column() {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp), color = Color.Gray
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(modifier = Modifier.height(8.dp)) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}