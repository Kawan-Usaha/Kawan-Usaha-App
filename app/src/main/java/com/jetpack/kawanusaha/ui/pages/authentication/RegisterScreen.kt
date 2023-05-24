package com.jetpack.kawanusaha.ui.pages.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
fun RegisterScreen(viewModel: LoginViewModel, navToVerification: () -> Unit, navToLanding: () -> Unit) {
    val authStatus by viewModel.registerCredential.collectAsState(initial = null)
    val coroutineScope = rememberCoroutineScope()
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(
            onClick = {
                viewModel.register(name = name.toString(), email = email.toString(), password = password.toString(), passwordConfirm = confirmPassword.toString())
            }
        ) {
            Text("Register")
        }
        Row {
            Text(text = "Already have an account? ")
            Text(text = "Sign In Now!", modifier = Modifier
                .clickable {
                    coroutineScope.launch {
                        viewModel.generate(null)
                        if (viewModel.isGenerated.value){
                            navToVerification()
                        }
                        // TODO else loading
                    }
                })
        }
    }

    BackPressHandler(onBackPressed = navToLanding)

    // Authentication Status Changes
    LaunchedEffect(authStatus) {
        if (authStatus?.data != null) {
            navToVerification()
        }
    }
}