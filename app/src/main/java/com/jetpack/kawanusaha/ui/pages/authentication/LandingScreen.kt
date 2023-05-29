package com.jetpack.kawanusaha.ui.pages.authentication

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
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
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.ui.BackPressHandler
import com.jetpack.kawanusaha.ui.theme.*

@Composable
fun LandingScreen(navToLogin: () -> Unit, navToRegister: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.padding(20.dp)
        )
        Text(
            stringResource(id = R.string.app_name)
        )

        Gradient(navToLogin, navToRegister)
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
                    .padding(top = 150.dp)
            ) {
                Text("LOGIN")
            }
            Button(
                onClick = { navToRegister() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp)
            ) {
                Text("REGISTER")
            }
        }
    }
}

