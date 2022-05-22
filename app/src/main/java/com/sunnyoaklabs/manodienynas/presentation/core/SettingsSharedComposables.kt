package com.sunnyoaklabs.manodienynas.presentation.core

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.sunnyoaklabs.manodienynas.BuildConfig
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.presentation.login.fragment.settings.dialog.DialogTextSettingsLogin
import com.sunnyoaklabs.manodienynas.ui.custom.LocalSpacing

@Composable
fun ItemAppDescription(
    appDescription: String,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.padding(
            top = LocalSpacing.current.medium,
            start = LocalSpacing.current.small,
            end = LocalSpacing.current.small
        ),
        elevation = 10.dp,
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    showDialog = true
                }
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = LocalSpacing.current.medium,
                    vertical = LocalSpacing.current.medium
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    tint = Color.Gray,
                    painter = painterResource(id = R.drawable.ic_description),
                    contentDescription = stringResource(id = R.string.app_description),
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
                Text(
                    modifier = Modifier.padding(start = LocalSpacing.current.small),
                    text = AnnotatedString(stringResource(id = R.string.app_description)),
                )
                DialogTextSettingsLogin(
                    showDialog,
                    stringResource(id = R.string.app_description),
                    "$appDescription ${BuildConfig.VERSION_NAME}v",
                    onDismiss = {showDialog = false},
                    onNegativeClick = {showDialog = false}
                )
            }
        }
    }
}

@Composable
fun ItemAppLicense(
    appLicense: String,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.padding(
            top = LocalSpacing.current.medium,
            start = LocalSpacing.current.small,
            end = LocalSpacing.current.small
        ),
        elevation = 10.dp
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    showDialog = true
                }
        ) {
            Row(
                modifier = modifier.padding(
                    horizontal = LocalSpacing.current.medium,
                    vertical = LocalSpacing.current.medium
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    tint = Color.Gray,
                    painter = painterResource(id = R.drawable.ic_license),
                    contentDescription = stringResource(id = R.string.app_license),
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
                Text(
                    modifier = Modifier.padding(start = LocalSpacing.current.small),
                    text = AnnotatedString(stringResource(id = R.string.app_license)),
                )
                DialogTextSettingsLogin(
                    showDialog,
                    stringResource(id = R.string.app_license),
                    appLicense,
                    onDismiss = {showDialog = false},
                    onNegativeClick = {showDialog = false}
                )
            }
        }
    }
}

@SuppressLint("QueryPermissionsNeeded")
@Composable
fun ItemReportABug(
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier.padding(
            top = LocalSpacing.current.medium,
            start = LocalSpacing.current.small,
            end = LocalSpacing.current.small
        ),
        elevation = 10.dp
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    val addresses = arrayOf("sunnyoaklabs@gmail.com")
                    val subject = "Tema: Mano dienynas lite"
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data = Uri.parse("mailto:")
                    intent.putExtra(Intent.EXTRA_EMAIL, addresses)
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }
        ) {
            Row(
                modifier = modifier.padding(
                    horizontal = LocalSpacing.current.medium,
                    vertical = LocalSpacing.current.medium
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    tint = Color.Gray,
                    painter = painterResource(id = R.drawable.ic_bug),
                    contentDescription = stringResource(id = R.string.report_a_bug),
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
                Text(
                    modifier = Modifier.padding(start = LocalSpacing.current.small),
                    text = AnnotatedString(stringResource(id = R.string.report_a_bug)),
                )
            }
        }
    }
}