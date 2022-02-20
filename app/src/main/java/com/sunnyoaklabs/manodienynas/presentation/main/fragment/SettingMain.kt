package com.sunnyoaklabs.manodienynas.presentation.main.fragment

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sunnyoaklabs.manodienynas.MainActivity
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.Person
import com.sunnyoaklabs.manodienynas.domain.model.SchoolInfo
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import com.sunnyoaklabs.manodienynas.presentation.login.LoginViewModel
import com.sunnyoaklabs.manodienynas.presentation.login.fragment.destinations.LoginFragmentDestination
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.SettingsMainFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.custom.LocalSpacing
import com.sunnyoaklabs.manodienynas.ui.theme.primaryGreenAccent

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
        ItemSchoolSelected(mainViewModel)
        ItemLogout(settingsMainFragmentViewModel)
    }
}

@Composable
fun ItemKeepSignedIn(
    settingsMainFragmentViewModel: SettingsMainFragmentViewModel,
    modifier: Modifier = Modifier
) {
    val settings = settingsMainFragmentViewModel.settingsState.value
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
                    settingsMainFragmentViewModel.deleteSetting()
                    settingsMainFragmentViewModel.insertSettings(
                        Settings(
                            !settings.keepSignedIn,
                            settings.selectedSchool
                        )
                    )
                    settingsMainFragmentViewModel.getSetting()
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
                    checked = settings.keepSignedIn,
                    onCheckedChange = {
                        settingsMainFragmentViewModel.deleteSetting()
                        settingsMainFragmentViewModel.insertSettings(
                            Settings(
                                !settings.keepSignedIn,
                                settings.selectedSchool
                            )
                        )
                        settingsMainFragmentViewModel.getSetting()
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
fun ItemSchoolSelected(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val person = mainViewModel.personState.value.person
    val settings = mainViewModel.settingsMainFragmentViewModel.settingsState.value

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
                        setShowDialog(true)
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
                AnimatedVisibility(visible = !mainViewModel.personState.value.isLoading) {
                    Column(
                        modifier = Modifier.padding(start = LocalSpacing.current.small)
                    ) {
                        Text(
                            text = AnnotatedString(settings.selectedSchool?.schoolName ?: ""),
                            color = Color.White
                        )
                        Row {
                            Text(
                                text = AnnotatedString(settings.selectedSchool?.role ?: ""),
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
                TextDialogChangeRole(
                    person,
                    mainViewModel,
                    mainViewModel.settingsMainFragmentViewModel,
                    showDialog,
                    setShowDialog
                )
            }
            if (mainViewModel.personState.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp))
            }
        }
    }
}

@Composable
fun ItemLogout(
    settingsMainFragmentViewModel: SettingsMainFragmentViewModel,
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
                TextDialogLogout(
                    settingsMainFragmentViewModel,
                    showDialog,
                    stringResource(id = R.string.logout_notice),
                    setShowDialog
                )
            }
        }
    }
}

@Composable
fun TextDialogChangeRole(
    person: Person?,
    mainViewModel: MainViewModel,
    settingsMainFragmentViewModel: SettingsMainFragmentViewModel,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.padding(0.dp),
            onDismissRequest = {},
            confirmButton = {},
            title = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    items(person?.schoolsNames ?: emptyList()) {
                        ChangeRoleListItem(it, mainViewModel, settingsMainFragmentViewModel,  setShowDialog)
                    }
                }
            },
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
        )
    }
}

@Composable
fun ChangeRoleListItem(
    schoolInfo: SchoolInfo,
    mainViewModel: MainViewModel,
    settingsMainFragmentViewModel: SettingsMainFragmentViewModel,
    setShowDialog: (Boolean) -> Unit,
    context: Context  = LocalContext.current,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .padding(
                top = 2.dp,
                bottom = 2.dp
            )
            .fillMaxWidth(),
        elevation = 10.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val settings = settingsMainFragmentViewModel.settingsState.value
                    if (schoolInfo.schoolId != settings.selectedSchool?.schoolId) {
                        settingsMainFragmentViewModel.deleteSetting()
                        settingsMainFragmentViewModel.insertSettings(
                            Settings(
                                settings.keepSignedIn,
                                schoolInfo
                            )
                        )
                        setShowDialog(false)
                        val intent = Intent(context, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent)
                    } else {
                        setShowDialog(false)
                    }
                }
        ) {
            Row(
                modifier = modifier
                    .padding(
                        horizontal = LocalSpacing.current.medium,
                        vertical = LocalSpacing.current.medium
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    tint = Color.Gray,
                    painter = painterResource(id = R.drawable.ic_code),
                    contentDescription = stringResource(id = R.string.logout),
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                )
                Column(
                    modifier = Modifier.padding(start = LocalSpacing.current.small)
                ) {
                    Text(
                        text = AnnotatedString(schoolInfo.schoolName),
                    )
                    Text(
                        text = AnnotatedString(schoolInfo.role),
                        color = Color.Gray
                    )
                }
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


@Composable
fun TextDialogLogout(
    settingsMainFragmentViewModel: SettingsMainFragmentViewModel,
    showDialog: Boolean,
    title: String,
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
                Row {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 2.dp, bottom = 2.dp, start = 2.dp, end = 2.dp),
                        colors = ButtonDefaults.buttonColors(primaryGreenAccent),
                        onClick = {
                            settingsMainFragmentViewModel.logout()
                        },
                    ) {
                        Text(stringResource(id = R.string.ok))
                    }
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 2.dp, bottom = 2.dp, start = 2.dp, end = 2.dp),
                        colors = ButtonDefaults.buttonColors(primaryGreenAccent),
                        onClick = {
                            setShowDialog(false)
                        },
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                }
            },
        )
    }
}