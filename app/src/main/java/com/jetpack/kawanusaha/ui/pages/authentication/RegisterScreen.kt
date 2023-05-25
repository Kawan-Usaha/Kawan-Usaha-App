package com.jetpack.kawanusaha.ui.pages.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.ui.BackPressHandler
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(viewModel: LoginViewModel, navToMain: () -> Unit, navToLanding: () -> Unit, navToLogin: () -> Unit) {
    val authStatus by viewModel.registerCredential.collectAsState(initial = null)
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if (password.text.length < 8)
                    Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
            }
        )
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if (confirmPassword.text != password.text)
                    Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
            }
        )
        Button(
            onClick = {
                viewModel.register(name = name.text, email = email.text, password = password.text, passwordConfirm = confirmPassword.text)
            }
        ) {
            Text("Register")
        }
        Row {
            Text(text = "Already have an account? ")
            Text(text = "Sign In Now!", modifier = Modifier
                .clickable {
                    navToLogin()
                })
        }
    }

    BackPressHandler(onBackPressed = navToLanding)

    LaunchedEffect( authStatus ){
        if(viewModel.isLoggedIn()){
            navToMain()
        }
    }
}