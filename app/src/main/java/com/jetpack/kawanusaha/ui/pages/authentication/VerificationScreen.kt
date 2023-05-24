package com.jetpack.kawanusaha.ui.pages.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.main.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun VerificationScreen(
    viewModel: LoginViewModel,
    email: String?,
    password: String?,
    passwordConfirm: String?,
    navToLogin: () -> Unit,
    navToMain: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var verificationCode = ""
    Card(backgroundColor = MaterialTheme.colors.background, elevation = 1.dp) {
        LazyColumn (modifier = Modifier.fillMaxSize()){
            item {
                Text(text = "KAWAN USAHA")
            }
            // TODO IMAGE HERE
//            item {  }
            item {
                Text(text = "Verification Code")
            }
            item {
                Text(text = "Please enter bla bla bla ...")
            }
            item {
                TextField(
                    value = verificationCode,
                    onValueChange = { verificationCode = it },
                    label = { Text(text = "Verify") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
            }
            item {
                Text(text = "Resend Code", modifier = Modifier.clickable {
                    // re-generate code
                    coroutineScope.launch {
                        viewModel.generate(email)
                    }
                })
            }
            item {
                Button (
                    onClick = {
                        viewModel.verify(verificationCode, password, passwordConfirm)
                        if (viewModel.isVerified.value){
                            navToLogin()
                        }
                        // TODO else show pop up that verification failed
                    }) {
                    Text(text = "Verify")
                }
                Text(text = "Verify email later", modifier = Modifier.clickable {
                    if (password != null || passwordConfirm != null){
                        navToMain()
                    }
                })
            }
        }
    }
}
