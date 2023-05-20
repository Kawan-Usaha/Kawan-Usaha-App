package com.jetpack.kawanusaha.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import com.jetpack.kawanusaha.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.main.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Username")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(
            onClick = {
                viewModel.login(email = email.toString(), password = password.toString())
            }
        ) {
            Text("Log In")
        }
        OAuthButton()
    }
}


@Composable
fun OAuthButton() {
    Image(
        painter = painterResource(R.drawable.google),
        contentDescription = "Google Logo",
        modifier = Modifier
            .padding(top = 20.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClick = { oauth() },
                onClickLabel = "Google Authentication"
            )
    )
}

fun oauth(){
    Log.e("LoginScreen", "OAuth Function")
}