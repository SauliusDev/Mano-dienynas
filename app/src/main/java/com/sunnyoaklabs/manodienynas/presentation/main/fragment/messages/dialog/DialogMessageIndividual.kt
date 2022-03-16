package com.sunnyoaklabs.manodienynas.presentation.main.fragment.messages.dialog

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.MessageFile
import com.sunnyoaklabs.manodienynas.domain.model.MessageIndividual
import com.sunnyoaklabs.manodienynas.presentation.core.CustomDialogCancelButton
import com.sunnyoaklabs.manodienynas.ui.theme.accentGreyLight
import com.sunnyoaklabs.manodienynas.ui.theme.primaryGreenAccent
import com.sunnyoaklabs.manodienynas.ui.theme.primaryVariantGreenLight

@Composable
fun DialogMessageIndividual(
    showDialog: Boolean,
    messageIndividual: MessageIndividual,
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
                        Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                    ) {
                        Text(messageIndividual.title, fontSize = 14.sp, color = primaryVariantGreenLight)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(messageIndividual.date, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        //FilesList(messageIndividual)
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyColumn(Modifier.heightIn(0.dp, 300.dp)) {
                            item {
                                Text(messageIndividual.content)
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

//@Composable
//fun FilesList(
//    messageIndividual: MessageIndividual,
//) {
//    Spacer(modifier = Modifier.height(4.dp))
//    LazyRow {
//        items(messageIndividual.files) {
//            FileItem(it)
//        }
//    }
//    Spacer(modifier = Modifier.height(4.dp))
//}

//@Composable
//private fun FileItem(
//    messageFile: MessageFile,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier.clickable {
//            Log.e("console log", "file: "+messageFile.url)
//        }.padding(4.dp),
//        elevation = 4.dp
//    ) {
//        Column(
//            modifier = Modifier.padding(4.dp).background(accentGreyLight),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                modifier = Modifier.size(15.dp),
//                painter = painterResource(id = R.drawable.ic_attachment),
//                contentDescription = stringResource(R.string.ic_attachment_description),
//                tint = Color.Gray
//            )
//        }
//    }
//}