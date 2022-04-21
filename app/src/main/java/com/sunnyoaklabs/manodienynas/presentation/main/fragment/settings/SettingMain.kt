package com.sunnyoaklabs.manodienynas.presentation.main.fragment.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import com.sunnyoaklabs.manodienynas.presentation.core.ItemAppDescription
import com.sunnyoaklabs.manodienynas.presentation.core.ItemAppLicense
import com.sunnyoaklabs.manodienynas.presentation.core.ItemReportABug
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.settings.dialog.DialogChangeRole
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.settings.dialog.DialogLogout
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.settings.dialog.DialogTextSettingsMain
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.SettingsMainFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.custom.LocalSpacing

@Composable
fun SettingsMainFragment(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val settingsMainFragmentViewModel = mainViewModel.settingsMainFragmentViewModel

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        ItemKeepSignedIn(settingsMainFragmentViewModel)
        ItemAppDescription(mainViewModel.getAppDescription())
        ItemAppLicense(mainViewModel.getAppLicense())
        ItemReportABug()
        ItemSchoolSelected(mainViewModel)
        ItemLogout(settingsMainFragmentViewModel)
    }
}

@Composable
private fun ItemKeepSignedIn(
    settingsMainFragmentViewModel: SettingsMainFragmentViewModel,
    modifier: Modifier = Modifier
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
                    settingsMainFragmentViewModel.updateSettings(
                        Settings(
                            !(settingsMainFragmentViewModel.settingsState.value.settings?.keepSignedIn ?: false),
                            settingsMainFragmentViewModel.settingsState.value.settings?.selectedSchool
                        )
                    )
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
                    checked = settingsMainFragmentViewModel.settingsState.value.settings?.keepSignedIn ?: false,
                    onCheckedChange = {
                        settingsMainFragmentViewModel.updateSettings(
                            Settings(
                                !(settingsMainFragmentViewModel.settingsState.value.settings?.keepSignedIn ?: false),
                                settingsMainFragmentViewModel.settingsState.value.settings?.selectedSchool
                            )
                        )
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
private fun ItemSchoolSelected(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    settingsMainFragmentViewModel: SettingsMainFragmentViewModel = mainViewModel.settingsMainFragmentViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    val person = mainViewModel.personState.value.person
    val settings = settingsMainFragmentViewModel.settingsState.value.settings

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
                    if (!mainViewModel.personState.value.isLoading) {
                        showDialog = true
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
                    painter = painterResource(id = R.drawable.ic_school),
                    contentDescription = stringResource(id = R.string.school),
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
                AnimatedVisibility(visible = !mainViewModel.personState.value.isLoading && !settingsMainFragmentViewModel.settingsState.value.isLoading) {
                    Column(
                        modifier = Modifier.padding(start = LocalSpacing.current.small)
                    ) {
                        Text(
                            text = AnnotatedString(settings?.selectedSchool?.schoolName ?: ""),
                        )
                        Row() {
                            Text(
                                text = AnnotatedString(settings?.selectedSchool?.role ?: ""),
                                color = Color.Gray
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(4.dp)
                                    .background(Color.Gray)
                            )
                            Text(
                                text = AnnotatedString(person?.name ?: ""),
                                color = Color.Gray,
                            )
                        }
                    }
                }
                DialogChangeRole(
                    person,
                    mainViewModel.settingsMainFragmentViewModel,
                    showDialog,
                    onNegativeClick = {showDialog = false},
                    onDismiss = {showDialog = false}
                )
            }
            if (mainViewModel.personState.value.isLoading || settingsMainFragmentViewModel.settingsState.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp))
            }
        }
    }
}

@Composable
private fun ItemLogout(
    settingsMainFragmentViewModel: SettingsMainFragmentViewModel,
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
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = stringResource(id = R.string.logout),
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
                Text(
                    modifier = Modifier.padding(start = LocalSpacing.current.small),
                    text = AnnotatedString(stringResource(id = R.string.logout)),
                )
                DialogLogout(
                    showDialog,
                    stringResource(id = R.string.logout_notice),
                    onPositiveClick = {
                        settingsMainFragmentViewModel.logout()
                    },
                    onNegativeClick = {showDialog = false},
                    onDismiss = {showDialog = false}
                )
            }
        }
    }
}