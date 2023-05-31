package com.jetpack.kawanusaha.ui.pages.authentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.ui.pages.main.SectionText

// TODO Security Leak in passing password
@Composable
fun ForgotPasswordScreen(
    navBack: () -> Unit,
    navToVerification: (email: String, password: String, passwordConfirm: String) -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var newPass by remember { mutableStateOf(TextFieldValue("")) }
    var confPass by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val orange = MaterialTheme.colors.secondary
    val white = MaterialTheme.colors.primary

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(190.dp),
            ) {
                drawCircle(
                    color = orange,
                    style = Stroke(width = 4.dp.toPx()),
                )
                drawCircle(
                    color = white
                )
            }
            Image(
                painter = painterResource(R.drawable.baseline_key_day),
                contentDescription = "key",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(120.dp)
            )
        }
        Card(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.primary,
            border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 32.dp)
                .offset(y = 15.dp)
        ) {
            Column(
                modifier = Modifier.padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    SectionText(
                        text = "RESET PASSWORD",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
                Spacer(Modifier.height(15.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email Icon",
                            tint = orange
                        )
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .padding(5.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colors.onPrimary,
                        cursorColor = MaterialTheme.colors.onPrimary,
                        errorBorderColor = MaterialTheme.colors.error,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.surface,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = MaterialTheme.colors.surface
                    ),
                )
                OutlinedTextField(
                    value = newPass,
                    onValueChange = { newPass = it },
                    label = { Text("New Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password Icon",
                            tint = orange
                        )
                    },
                    trailingIcon = {
                        val image = if (passwordVisible)
                            painterResource(R.drawable.baseline_visibility_24) else
                            painterResource(R.drawable.baseline_visibility_off_24)

                        val description =
                            if (passwordVisible) "Hide Password" else "Show Password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(painter = image, contentDescription = description)
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .padding(5.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colors.onPrimary,
                        cursorColor = MaterialTheme.colors.onPrimary,
                        errorBorderColor = MaterialTheme.colors.error,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.surface,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = MaterialTheme.colors.surface
                    ),
                )
                Spacer(Modifier.height(15.dp))
                OutlinedTextField(
                    value = confPass,
                    onValueChange = { confPass = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password Icon",
                            tint = orange
                        )
                    },
                    trailingIcon = {
                        val image = if (passwordVisible)
                            painterResource(R.drawable.baseline_visibility_24) else
                            painterResource(R.drawable.baseline_visibility_off_24)

                        val description =
                            if (passwordVisible) "Hide Password" else "Show Password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(painter = image, contentDescription = description)
                        }

                        if (newPass.text != confPass.text)
                            Icon(
                                Icons.Filled.Warning,
                                "error",
                                tint = MaterialTheme.colors.error
                            )
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .padding(5.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colors.onPrimary,
                        cursorColor = MaterialTheme.colors.onPrimary,
                        errorBorderColor = MaterialTheme.colors.error,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.surface,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = MaterialTheme.colors.surface
                    ),
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = {
                        navToVerification(email.text, newPass.text, confPass.text)
                    },
                    enabled = (email.text != "" && newPass.text.length >= 8 && confPass.text.length >= 8),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        contentColor = MaterialTheme.colors.onPrimary
                    ),
                    modifier = Modifier
                        .height(45.dp),
                ) {
                    Text(
                        text = "CHANGE PASSWORD",
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = {
                        navBack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.onPrimary
                    ),
                    modifier = Modifier
                        .height(45.dp),
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
        }
    }
}


