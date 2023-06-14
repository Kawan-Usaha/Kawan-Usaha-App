package com.jetpack.kawanusaha.ui.pages.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.ui.pages.BackPressHandler
import com.jetpack.kawanusaha.ui.pages.SectionText
import com.jetpack.kawanusaha.ui.pages.onKeyboardVisible
import com.jetpack.kawanusaha.ui.pages.scrollToItem
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navToRegister: () -> Unit,
    navToLanding: () -> Unit,
    navToMain: () -> Unit,
    navToForgotPassword: () -> Unit
) {
    val loginToken by viewModel.loginCredential.collectAsState(initial = null)
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val isKeyboardOpen by onKeyboardVisible()

    val secondaryColor = MaterialTheme.colors.secondary
    val chocolateVariant = MaterialTheme.colors.secondaryVariant

    LaunchedEffect(isKeyboardOpen) {
        if (isKeyboardOpen) {
            scrollToItem(lazyListState, 2)
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(color = chocolateVariant),
                    ) {
                        append("KAWAN")
                    }
                    withStyle(
                        style = SpanStyle(color = secondaryColor)
                    ) {
                        append(" USAHA")
                    }
                },
                style = MaterialTheme.typography.h4
            )
        }
        item {
            Card(
                shape = RoundedCornerShape(10.dp),
                backgroundColor = MaterialTheme.colors.primary,
                border = BorderStroke(1.dp, secondaryColor),
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
                            text = stringResource(R.string.login),
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier
                                .padding(5.dp)
                        )
                    }
                    Spacer(Modifier.height(15.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(R.string.email)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                        ),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = stringResource(R.string.email_icon),
                                tint = secondaryColor
                            )
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .imePadding()
                            .padding(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colors.onPrimary,
                            cursorColor = MaterialTheme.colors.onPrimary,
                            errorBorderColor = Color.Red,
                            focusedBorderColor = MaterialTheme.colors.secondary,
                            unfocusedBorderColor = MaterialTheme.colors.surface,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            unfocusedLabelColor = MaterialTheme.colors.surface
                        )
                    )
                    Spacer(Modifier.height(15.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(R.string.password)) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = stringResource(R.string.password_icon),
                                tint = secondaryColor
                            )
                        },
                        trailingIcon = {
                            val image = if (passwordVisible)
                                painterResource(R.drawable.baseline_visibility_24) else
                                painterResource(R.drawable.baseline_visibility_off_24)

                            val description =
                                if (passwordVisible) stringResource(R.string.hide_password) else stringResource(
                                    R.string.show_password
                                )
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(painter = image, contentDescription = description)
                            }
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .imePadding()
                            .padding(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colors.onPrimary,
                            cursorColor = MaterialTheme.colors.onPrimary,
                            errorBorderColor = Color.Red,
                            focusedBorderColor = MaterialTheme.colors.secondary,
                            unfocusedBorderColor = MaterialTheme.colors.surface,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            unfocusedLabelColor = MaterialTheme.colors.surface
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = stringResource(R.string.forgot_password),
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .clickable { navToForgotPassword() }
                            .padding(start = 5.dp, end = 5.dp)
                            .align(Alignment.End)
                    )

                    Spacer(modifier = Modifier.height(50.dp))
                    Button(
                        onClick = {
                            viewModel.login(email = email.text, password = password.text)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.secondary,
                            contentColor = MaterialTheme.colors.onPrimary
                        ),
                        modifier = Modifier
                            .height(45.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.login),
                            style = MaterialTheme.typography.h3,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = MaterialTheme.colors.onPrimary,
                            thickness = 1.dp
                        )

                        Text(
                            text = stringResource(R.string.or_login_with),
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .background(MaterialTheme.colors.primary)
                                .padding(start = 6.dp, end = 6.dp)
                        )
                    }
                    Spacer(Modifier.height(15.dp))
                    OAuthButton(viewModel)
                }
            }
        }
        item {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.dont_have_an_account),
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = stringResource(R.string.sign_up_now),
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable { navToRegister() }
                )
            }
        }
    }

    BackPressHandler(onBackPressed = navToLanding)

    // Authentication Status Changes
    LaunchedEffect(loginToken) {
        if (viewModel.isLoggedIn()) {
            navToMain()
        }
    }
}

@Composable
fun OAuthButton(viewModel: LoginViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf<String?>(null) }
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account != null) {
                    coroutineScope.launch {
                        viewModel.register(
                            name = account.displayName!!,
                            email = account.email!!,
                            password = account.idToken!!.slice(0..70),
                            passwordConfirm = account.idToken!!.slice(0..70)
                        )
                        viewModel.login(
                            email = account.email!!,
                            password = account.idToken!!.slice(0..70)
                        )
                    }
                } else {
                    text = "Google sign in failed"
                }
            } catch (e: ApiException) {
                text = "Google sign in failed"
            }
        }
    Image(
        painter = painterResource(R.drawable.google),
        contentDescription = "Google Logo",
        modifier = Modifier
            .padding(top = 10.dp)
            .size(50.dp)
            .clickable(
                enabled = true,
                onClick = { authResultLauncher.launch(signInRequestCode) },
                onClickLabel = "Google Authentication"
            )
    )
}

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, signInOptions)
}

class AuthResultContract : ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {
    override fun createIntent(context: Context, input: Int): Intent =
        getGoogleSignInClient(context).signInIntent.putExtra("input", input)

    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? {
        return when (resultCode) {
            Activity.RESULT_OK -> GoogleSignIn.getSignedInAccountFromIntent(intent)
            else -> null
        }
    }
}

