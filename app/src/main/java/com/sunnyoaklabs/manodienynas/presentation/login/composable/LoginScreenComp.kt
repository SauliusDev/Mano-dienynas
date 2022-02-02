package com.sunnyoaklabs.manodienynas.presentation.login.composable

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sunnyoaklabs.manodienynas.*
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.destinations.SettingScreenDestination
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.presentation.login.LoginViewModel
import com.sunnyoaklabs.manodienynas.ui.custom.LocalSpacing

@Composable
fun LoginScreenComp(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    /* TODO only for testing */
    var username by remember { mutableStateOf("abca68237@gmail.com") }
    var password by remember { mutableStateOf("KR10kly5") }
//    var username by remember { mutableStateOf(viewModel.credentials.username) }
//    var password by remember { mutableStateOf(viewModel.credentials.password) }

    var passwordVisibility by remember { mutableStateOf(false) }

    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (button) = createRefs()

        settingButton(
            navigator,
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(parent.top, margin = 32.dp)
                    end.linkTo(parent.end, margin = 64.dp)
                }
                .width(50.dp)
                .height(50.dp),
        )
    }

    Column(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(id = R.string.username)) },
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(
                    vertical = LocalSpacing.current.medium,
                    horizontal = LocalSpacing.current.extraLarge
                )
                .fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password)) },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            trailingIcon = {
                val image = if (passwordVisibility)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                IconButton(
                    onClick = {
                        passwordVisibility = !passwordVisibility
                    }
                ) {
                    Icon(imageVector  = image, "")
                }
            },
            modifier = Modifier
                .padding(
                    vertical = LocalSpacing.current.medium,
                    horizontal = LocalSpacing.current.extraLarge
                )
                .fillMaxWidth()
        )
        CheckboxItem(
            Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.deleteCredentials()
                    viewModel.insertCredentials(Credentials(username, password))
                    context.startActivity(
                        Intent(context, MainActivity::class.java)
                            .putExtra("initial", "Login from login activity")
                    )
                }
            },
            modifier = Modifier
                .padding(
                    vertical = LocalSpacing.current.medium,
                    horizontal = LocalSpacing.current.extraLarge
                )
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.sign_in))

        }

    }
}

@Composable
fun progressBar() {

}

@Composable
fun settingButton(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier.background(Color.Transparent),
        onClick = {
            navigator.navigate(
                SettingScreenDestination()
            )
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = stringResource(id = R.string.go_to_settings_screen),
            tint = Color.Gray,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
        )
    }
}

@Composable
fun CheckboxItem(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Checkbox(
            checked = viewModel.keepSignedIn,
            onCheckedChange = {
                viewModel.deleteKeepSignedIn()
                viewModel.insertKeepSignedIn(!viewModel.keepSignedIn)
                viewModel.getKeepSignedIn()
            }
        )
        Text(
            text = stringResource(id = R.string.keep_signed_in),
            modifier = Modifier.padding(start = LocalSpacing.current.small)
        )
    }
}