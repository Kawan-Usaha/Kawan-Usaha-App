package com.jetpack.kawanusaha.ui.pages.authentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.ui.BackPressHandler
import com.jetpack.kawanusaha.ui.pages.SectionText
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(viewModel: LoginViewModel, navToMain: () -> Unit, navToLanding: () -> Unit, navToLogin: () -> Unit) {
    val authStatus by viewModel.registerCredential.collectAsState(initial = null)
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val orange = MaterialTheme.colors.secondary
    val chocolateVariant = MaterialTheme.colors.secondaryVariant

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = chocolateVariant),
                ) {
                    append("KAWAN")
                }
                withStyle(
                    style = SpanStyle(color = orange)
                ) {
                    append(" USAHA")
                }
            },
            style = MaterialTheme.typography.h4
        )
        Spacer(Modifier.height(10.dp))
        Row (
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Already have an account? ",
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "Sign In Now!",
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                .clickable { navToLogin() }
            )
        }
        Spacer(Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.primary,
            border = BorderStroke(1.dp, colorResource(R.color.secondary_day)),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 32.dp)
                .offset(4.dp)
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
                        text = "REGISTER",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
                Spacer(Modifier.height(15.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                    ),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Name Icon",
                            tint = orange
                        )
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .padding(5.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.onBackground,
                        errorBorderColor = Color.Red,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = colorResource(R.color.grey),
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = colorResource(R.color.grey)
                    )
                )
                Spacer(Modifier.height(15.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                    ),
                    singleLine = true,
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
                        textColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.onBackground,
                        errorBorderColor = Color.Red,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = colorResource(R.color.grey),
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = colorResource(R.color.grey)
                    ),
                )
                Spacer(Modifier.height(15.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
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

                        val description = if (passwordVisible) "Hide Password" else "Show Password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(painter = image, contentDescription = description)
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .padding(5.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.onBackground,
                        errorBorderColor = MaterialTheme.colors.error,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = colorResource(R.color.grey),
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = colorResource(R.color.grey)
                    ),
                )
                Spacer(Modifier.height(15.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
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

                        val description = if (passwordVisible) "Hide Password" else "Show Password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(painter = image, contentDescription = description)
                        }

                        if (confirmPassword.text != password.text)
                            Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .padding(5.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.onBackground,
                        errorBorderColor = MaterialTheme.colors.error,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = colorResource(R.color.grey),
                        focusedLabelColor = MaterialTheme.colors.secondary,
                        unfocusedLabelColor = colorResource(R.color.grey)
                    ),
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = {
                        viewModel.register(
                            name = name.text,
                            email = email.text,
                            password = password.text,
                            passwordConfirm = confirmPassword.text
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.onBackground
                    ),
                    modifier = Modifier
                        .height(45.dp),
                ) {
                    Text(
                        text = "REGISTER",
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
        }
    }

    BackPressHandler(onBackPressed = navToLanding)

    LaunchedEffect( authStatus ){
        if(viewModel.isLoggedIn()){
            navToMain()
        }
    }
}