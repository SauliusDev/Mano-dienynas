package com.sunnyoaklabs.manodienynas.presentation.login.composable

import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.model.UserSettings
import com.sunnyoaklabs.manodienynas.presentation.login.LoginViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.ui.custom.LocalSpacing
import com.sunnyoaklabs.manodienynas.ui.theme.ManoDienynasTheme
import com.sunnyoaklabs.manodienynas.ui.theme.primaryGreenAccent

@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    appDescription: String,
    appLicense: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        ToolbarSettings(navigator)
        ItemKeepSignedIn()
        ItemAppDescription(appDescription)
        ItemAppLicense(appLicense)
    }
}

@Composable
fun ToolbarSettings(
    navigator: DestinationsNavigator
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.settings)) },
        backgroundColor = primaryGreenAccent,
        navigationIcon = {
            IconButton(
                onClick = {
                    /*TODO navigate back to setting */
                    navigator
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = stringResource(id = R.string.go_to_login_screen),
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun ItemKeepSignedIn(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var keepSignedIn by remember { mutableStateOf(viewModel.keepSignedIn) }

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
                    viewModel.deleteKeepSignedIn()
                    viewModel.insertKeepSignedIn(!keepSignedIn)
                    viewModel.getKeepSignedIn()
                    keepSignedIn = !keepSignedIn
                }
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = LocalSpacing.current.extraSmall,
                    vertical = LocalSpacing.current.small
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = keepSignedIn,
                    onCheckedChange = {
                        viewModel.deleteKeepSignedIn()
                        viewModel.insertKeepSignedIn(!keepSignedIn)
                        viewModel.getKeepSignedIn()
                        keepSignedIn = !keepSignedIn
                    }
                )
                Text(
                    text = stringResource(id = R.string.keep_signed_in)
                )
            }
        }
    }
}

@Composable
fun ItemAppDescription(
    appDescription: String,
    modifier: Modifier = Modifier
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

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
                    setShowDialog(true)
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
                TextDialog(
                    showDialog,
                    stringResource(id = R.string.app_description),
                    appDescription,
                    setShowDialog
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
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

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
                    setShowDialog(true)
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
                TextDialog(
                    showDialog,
                    stringResource(id = R.string.app_license),
                    appLicense,
                    setShowDialog
                )
            }
        }
    }
}

@Composable
fun TextDialog(
    showDialog: Boolean,
    title: String,
    description: String,
    setShowDialog: (Boolean) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.padding(0.dp),
            onDismissRequest = {
            },
            title = {
                Text(title)
            },
            confirmButton = {},
            dismissButton = {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(primaryGreenAccent),
                    onClick = {
                        setShowDialog(false)
                    },
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
            },
            text = {
                Text(description)
            },
        )
    }
}