package com.jetpack.kawanusaha.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.ui.BackPressHandler

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navToRegister: () -> Unit,
    navToLanding: () -> Unit,
    navToMain: () -> Unit,
    navToForgotPassword: () -> Unit
) {
    val loginToken by viewModel.loginToken.collectAsState(initial = null)
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
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Text(text = "Forgot Password?", modifier = Modifier.clickable { navToForgotPassword })
        Button(
            onClick = {
                viewModel.login(email = email.toString(), password = password.toString())
            }
        ) {
            Text("Log In")
        }
        OAuthButton()
        Row {
            Text(text = "Don't have an account? ")
            Text(text = "Sign Up Now!", modifier = Modifier
                .clickable { navToRegister() })
        }
    }
    
    BackPressHandler(onBackPressed = navToLanding)

    // Authentication Status Changes
    LaunchedEffect(loginToken) {
        if (loginToken != null) {
            navToMain()
        }
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

fun oauth() {
    Log.e("LoginScreen", "OAuth Function")
}