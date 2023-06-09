package com.jetpack.kawanusaha.ui.pages.authentication

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.ui.pages.BackPressHandler
import com.jetpack.kawanusaha.ui.theme.*

@Composable
fun LandingScreen(navToLogin: () -> Unit, navToRegister: () -> Unit) {
    val chocolateVariant = MaterialTheme.colors.secondaryVariant
    val secondaryColor = MaterialTheme.colors.secondary
    val boxSize = with(LocalDensity.current) { 300.dp.toPx() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .background(
                brush = Brush.linearGradient(
                    0.0f to Gradient1,
                    0.33f to PrimaryDay,
                    0.66f to PrimaryVariantDay,
                    1.00f to PrimaryNight,
                    start = Offset((0.3f) * boxSize, boxSize * (1.3f)),
                    end = Offset(boxSize, 0f),
                    tileMode = TileMode.Clamp
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.padding(20.dp)
        )
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
        Button(
            onClick = { navToLogin() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp)
                .padding(top = 50.dp),
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colors.secondary
            )
        ) {
            Text(stringResource(R.string.login))
        }
        Button(
            onClick = { navToRegister() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colors.secondary
            )
        ) {
            Text(stringResource(R.string.register))
        }
    }

    val activity = (LocalContext.current as? Activity)
    BackPressHandler(
        onBackPressed = { activity?.finish() }
    )
}

@Composable
fun Gradient(navToLogin: () -> Unit, navToRegister: () -> Unit) {
    val boxSize = with(LocalDensity.current) { 300.dp.toPx() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(WavyShape(period = 200.dp, amplitude = 20.dp))
            .background(
                brush = Brush.linearGradient(
                    0.0f to Gradient1,
                    0.33f to PrimaryDay,
                    0.66f to PrimaryVariantDay,
                    1.00f to PrimaryNight,
                    start = Offset((0.3f) * boxSize, boxSize * (1.3f)),
                    end = Offset(boxSize, 0f),
                    tileMode = TileMode.Clamp
                )
            )
    ) {
        Column(Modifier.fillMaxWidth()) {
            Button(
                onClick = { navToLogin() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp)
                    .padding(top = 150.dp),
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colors.secondary
                )
            ) {
                Text(stringResource(R.string.login))
            }
            Button(
                onClick = { navToRegister() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colors.secondary
                )
            ) {
                Text(stringResource(R.string.register))
            }
        }
    }
}

