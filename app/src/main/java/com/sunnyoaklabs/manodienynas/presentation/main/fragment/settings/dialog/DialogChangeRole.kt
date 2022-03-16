package com.sunnyoaklabs.manodienynas.presentation.main.fragment.settings.dialog

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sunnyoaklabs.manodienynas.MainActivity
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.domain.model.Person
import com.sunnyoaklabs.manodienynas.domain.model.SchoolInfo
import com.sunnyoaklabs.manodienynas.domain.model.Settings
import com.sunnyoaklabs.manodienynas.presentation.core.CustomDialogCancelButton
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.SettingsMainFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.custom.LocalSpacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DialogChangeRole(
    person: Person?,
    settingsMainFragmentViewModel: SettingsMainFragmentViewModel,
    showDialog: Boolean,
    onNegativeClick: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(person?.schoolsNames ?: emptyList()) {
                            ChangeRoleListItem(it, settingsMainFragmentViewModel, onHideDialog = {onNegativeClick()})
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    CustomDialogCancelButton(onNegativeClick)
                }
            }
        }
    }
}

@Composable
private fun ChangeRoleListItem(
    schoolInfo: SchoolInfo,
    settingsMainFragmentViewModel: SettingsMainFragmentViewModel,
    onHideDialog: () -> Unit,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(
                4.dp
            )
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val settings = settingsMainFragmentViewModel.settingsState.value.settings
                    if (schoolInfo.schoolId != settings?.selectedSchool?.schoolId) {
                        CoroutineScope(Dispatchers.IO).launch {
                            settingsMainFragmentViewModel
                                .updateSettings(
                                    Settings(
                                        settings?.keepSignedIn ?: false,
                                        schoolInfo
                                    )
                                )
                                .join()
                            onHideDialog()
                            val intent = Intent(context, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            context.startActivity(intent)
                        }
                    } else {
                        onHideDialog()
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
