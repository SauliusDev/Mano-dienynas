package com.sunnyoaklabs.manodienynas.presentation.login.fragment.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.presentation.core.ItemAppDescription
import com.sunnyoaklabs.manodienynas.presentation.core.ItemAppLicense
import com.sunnyoaklabs.manodienynas.presentation.core.ItemReportABug
import com.sunnyoaklabs.manodienynas.presentation.login.LoginViewModel
import com.sunnyoaklabs.manodienynas.presentation.login.fragment.destinations.LoginFragmentDestination
import com.sunnyoaklabs.manodienynas.ui.custom.LocalSpacing
import com.sunnyoaklabs.manodienynas.ui.theme.primaryGreenAccent

@Destination
@Composable
fun SettingsLoginFragment(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        ToolbarSettingsLogin(navigator)

        ItemKeepSignedInLogin(loginViewModel)
        ItemAppDescription(loginViewModel.getAppDescription())
        ItemAppLicense(loginViewModel.getAppLicense())
        ItemReportABug()
    }
}

@Composable
private fun ToolbarSettingsLogin(
    navigator: DestinationsNavigator
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.settings),
                color = colorResource(id = android.R.color.white)
            )
        },
        backgroundColor = primaryGreenAccent,
        navigationIcon = {
            IconButton(
                onClick = {
                    navigator.navigate(LoginFragmentDestination)
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
private fun ItemKeepSignedInLogin(
    loginViewModel: LoginViewModel,
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
                    if (!loginViewModel.keepSignedIn.isLoading) {
                        loginViewModel.updateKeepSignedIn(!(loginViewModel.keepSignedIn.settings?.keepSignedIn ?: false))
                    }
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
                    checked = loginViewModel.keepSignedIn.settings?.keepSignedIn ?: false,
                    onCheckedChange = {
                        if (!loginViewModel.keepSignedIn.isLoading) {
                            loginViewModel.updateKeepSignedIn(it)
                        }
                    }
                )
                Text(
                    text = stringResource(id = R.string.keep_signed_in)
                )
            }
        }
    }
}