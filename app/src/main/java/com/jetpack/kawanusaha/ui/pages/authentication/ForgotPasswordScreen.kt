package com.jetpack.kawanusaha.ui.pages.authentication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.ui.theme.Typography
import kotlinx.coroutines.launch

// TODO Security Leak in passing password
@Composable
fun ForgotPasswordScreen(
    viewModel: LoginViewModel,
    navBack: () -> Unit,
    navToVerification: (email: String, password: String, passwordConfirm: String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var newPass by remember { mutableStateOf(TextFieldValue("")) }
    var confPass by remember { mutableStateOf(TextFieldValue("")) }

    Card(backgroundColor = MaterialTheme.colors.background, elevation = 1.dp) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Text(text = "RESET PASSWORD", style = Typography.h2)
            }
            item {
                Text(text = "Create a new bla bla bla ...", style = Typography.h4)
            }
            item {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )
            }
            item {
                TextField(
                    value = newPass,
                    onValueChange = {newPass = it},
                    label = { Text(text = "New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        if (newPass.text.length < 8 && newPass.text.length != 0)
                            Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colors.error)
                    },
                )
            }
            item {
                TextField(
                    value = confPass,
                    onValueChange = {confPass = it},
                    label = { Text(text = "Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        if (confPass.text != newPass.text)
                            Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colors.error)
                    },
                )
            }
            item {
                Button(
                    onClick = {
                        // generate verification code
                        navToVerification(email.text, newPass.text, confPass.text)
                    },
                    enabled = (email.text != "" && newPass.text.length >= 8 && confPass.text.length >= 8)
                ) {
                    Text(text = "Verify")
                }
            }
            item {
                Button(
                    onClick = {
                        navBack()
                    }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}


