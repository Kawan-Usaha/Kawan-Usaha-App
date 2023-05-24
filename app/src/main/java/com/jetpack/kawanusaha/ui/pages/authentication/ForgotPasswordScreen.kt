package com.jetpack.kawanusaha.ui.pages.authentication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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
    var email = ""
    var newPass = ""
    var confPass = ""

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
                    onValueChange = { newPass = it },
                    label = { Text(text = "New Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
            }
            item {
                TextField(
                    value = confPass,
                    onValueChange = { confPass = it },
                    label = { Text(text = "Confirm Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
            }
            item {
                Button(
                    onClick = {
                        // generate verification code
                        coroutineScope.launch {
                            viewModel.generate(email)
                            if (viewModel.isGenerated.value){
                                //nav to verification
                                navToVerification(email, newPass, confPass)
                            }
                            // TODO else loading
                        }
                    },
                    enabled = (email != "" && newPass.length > 8 && confPass.length > 8)
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


