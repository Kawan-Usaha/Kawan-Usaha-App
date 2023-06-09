package com.jetpack.kawanusaha.ui.pages.authentication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.main.MainViewModel

@Composable
fun VerificationScreen(
    viewModel: LoginViewModel,
    mainViewModel: MainViewModel,
    email: String?,
    password: String?,
    passwordConfirm: String?,
    navToMain: () -> Unit,
) {
    val mContext = LocalContext.current
    var verificationCode by remember { mutableStateOf(TextFieldValue("")) }
    val isVerified by viewModel.isVerified.collectAsState(false)
    val emails by remember{ mutableStateOf(email) }
    val user by mainViewModel.userProfile.collectAsState(initial = null)

    Generate(viewModel = viewModel, email = email)
    LazyColumn(
        modifier = Modifier.fillMaxSize().safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            val white = MaterialTheme.colors.primary
            val orange = MaterialTheme.colors.secondary
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(color = MaterialTheme.colors.secondaryVariant),
                    ) {
                        append("KAWAN")
                    }
                    withStyle(
                        style = SpanStyle(color = MaterialTheme.colors.secondary)
                    ) {
                        append(" USAHA")
                    }
                },
                style = MaterialTheme.typography.h4
            )
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
                    painter = painterResource(R.drawable.email_open),
                    contentDescription = stringResource(R.string.email_open),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(120.dp)
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
        item{
            Box {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    drawRoundRect(
                        color = Color.White,
                        cornerRadius = CornerRadius(16f, 16f)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.title_verify),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                    )
                    Text(
                        text = stringResource(R.string.body_verify, user?.data?.email ?: ""),
                        style = MaterialTheme.typography.body1,
                    )
                    Spacer(modifier = Modifier.height(35.dp))
                    Text(
                        text = stringResource(R.string.check_email),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = verificationCode,
                        onValueChange = { verificationCode = it },
                        label = {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.enter_code),
                                    style = MaterialTheme.typography.body1,
                                    textAlign = TextAlign.Center
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Characters
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .fillMaxWidth()
                            .height(120.dp)
                            .imePadding()
                            .padding(start = 50.dp, end = 50.dp),
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp
                        ),
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
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = stringResource(R.string.resend_code),
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            // re-generate code
                            viewModel.generate(email)
                        })
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        // TODO else show pop up that verification failed
                        onClick = {
                            viewModel.verify(
                                verificationCode.text,
                                password,
                                passwordConfirm
                            )
                        },
                        modifier = Modifier.size(300.dp, 40.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                    ) {
                        Text(
                            text = stringResource(R.string.verify),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(R.string.verify_email_later),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                                navToMain()
                        }
                    )

                }
            }
        }
    }
    LaunchedEffect(isVerified) {
        if (isVerified) {
            viewModel.login(email.toString(), password.toString())
            navToMain()
            viewModel.clear()
        } else {
            mToast(mContext, viewModel.message.value)
        }
    }
}

@Composable
fun Generate(viewModel: LoginViewModel, email: String?) {
    LaunchedEffect(viewModel) {
        viewModel.generate(email)
    }
}

fun mToast(context: Context, text: String) {
    if (text.isNotEmpty()) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}
