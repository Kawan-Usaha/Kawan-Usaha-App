package com.jetpack.kawanusaha.ui.pages.authentication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.main.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun VerificationScreen(
    viewModel: LoginViewModel,
    email: String?,
    password: String?,
    passwordConfirm: String?,
    navToMain: () -> Unit
) {
    val mContext = LocalContext.current
    var verificationCode by remember { mutableStateOf(TextFieldValue("")) }
    val isVerified by viewModel.isVerified.collectAsState(false)
    Generate(viewModel = viewModel, email = email)
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
                    viewModel.generate(email)
                })
            }
            item {
                Button (
                    onClick = {
                        viewModel.verify(verificationCode.text, password, passwordConfirm)
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
    LaunchedEffect(isVerified){
        if(isVerified){
            navToMain()
        } else {
            mToast(mContext, viewModel.message.value)
        }
    }
}

@Composable
fun Generate(viewModel: LoginViewModel, email: String?){
    LaunchedEffect(viewModel){
        viewModel.generate(email)
    }
}

fun mToast (context: Context, text: String){
    if (text.isNotEmpty()){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}
