package com.sunnyoaklabs.manodienynas.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.sunnyoaklabs.manodienynas.domain.model.Credentials

@Composable
fun LoginScreen(
    credentials: Credentials,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf(credentials.username) }
    var password by remember { mutableStateOf(credentials.password) }

    Column(modifier = modifier) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Enter username") },
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(20.dp)
        ) {
            Text(text = "Login")

        }

    }
}