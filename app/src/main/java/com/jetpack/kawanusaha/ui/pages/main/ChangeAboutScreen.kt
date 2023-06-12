package com.jetpack.kawanusaha.ui.pages.main

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.MainActivity
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.CameraViewModel
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.authentication.mToast

@Composable
fun ChangeAboutScreen(mainViewModel: MainViewModel, navToCamera: () -> Unit, navBack: () -> Unit) {
    var newName by remember { mutableStateOf(TextFieldValue("")) }
    var newEmail by remember { mutableStateOf(TextFieldValue("")) }
    val status by mainViewModel.status.collectAsState(initial = false)

    val image by mainViewModel.imageFile.collectAsState(initial = Uri.parse("file://dev/null"))

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.profile))
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        },
        modifier = Modifier.safeDrawingPadding()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Column {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        if (image != Uri.parse("file://dev/null")) {
                            AsyncImage(
                                model = image,
                                contentDescription = stringResource(R.string.image_preview),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(200.dp)
                                    .padding(8.dp)
                                    .clip(CircleShape)
                            )

                            IconButton(
                                onClick = { mainViewModel.clearImage() },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(80.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = stringResource(R.string.delete_picture),
                                    tint = Color.White,
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colors.secondary,
                                            shape = CircleShape
                                        )
                                        .size(45.dp)
                                )
                            }
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = "Failed to get Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(200.dp)
                                    .padding(8.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Edit,
                            tint = Color.White,
                            contentDescription = stringResource(R.string.edit_profile_picture),
                            modifier = Modifier
                                .size(40.dp)
                                .alpha(0.8f)
                                .clickable { navToCamera() }
                        )
                    }
                }
            }
            item {
                Card(
                    backgroundColor = MaterialTheme.colors.primary,
                    elevation = 5.dp,
                    modifier = Modifier.padding(10.dp),
                    border = BorderStroke(width = 1.dp, MaterialTheme.colors.secondary)
                ) {
                    val user by mainViewModel.userProfile.collectAsState(initial = null)
                    val customTextSelection = TextSelectionColors(
                        handleColor = MaterialTheme.colors.secondary,
                        backgroundColor = MaterialTheme.colors.background
                    )
                    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelection) {
                        if (user != null) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Text(
                                    text = user?.data?.name.toString(),
                                    style = MaterialTheme.typography.h3
                                )
                                user?.data?.let { userData ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.padding(
                                            horizontal = 16.dp,
                                            vertical = 8.dp
                                        )
                                    ) {

                                        Text(
                                            text = stringResource(R.string.account_id) + ": ",
                                            fontWeight = FontWeight.SemiBold,
                                            style = MaterialTheme.typography.body1,
                                            modifier = Modifier.weight(0.3f)
                                        )

                                        Spacer(modifier = Modifier.width(100.dp))
                                        TextField(
                                            value = userData.userId,
                                            onValueChange = { },
                                            readOnly = true,
                                            colors = TextFieldDefaults.textFieldColors(
                                                backgroundColor = MaterialTheme.colors.primary,
                                                disabledIndicatorColor = MaterialTheme.colors.primary,
                                                focusedIndicatorColor = MaterialTheme.colors.onPrimary,
                                            ),
                                            modifier = Modifier.weight(0.7f)
                                        )

                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.padding(
                                            horizontal = 16.dp,
                                            vertical = 8.dp
                                        )
                                    ) {
                                        newName = TextFieldValue(userData.name)
                                        Text(
                                            text = stringResource(R.string.name) + ": ",
                                            fontWeight = FontWeight.SemiBold,
                                            style = MaterialTheme.typography.body1,
                                            modifier = Modifier.weight(0.3f)
                                        )
                                        Spacer(modifier = Modifier.width(100.dp))
                                        TextField(
                                            value = newName,
                                            onValueChange = {
                                                newName = it
                                            },
                                            colors = TextFieldDefaults.textFieldColors(
                                                backgroundColor = MaterialTheme.colors.primary,
                                                disabledIndicatorColor = MaterialTheme.colors.primary,
                                                focusedIndicatorColor = MaterialTheme.colors.onPrimary,
                                                cursorColor = MaterialTheme.colors.onPrimary,
                                            ),
                                            modifier = Modifier.weight(0.7f),
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.padding(
                                            horizontal = 16.dp,
                                            vertical = 8.dp
                                        )
                                    ) {
                                        newEmail = TextFieldValue(userData.email)
                                        Text(
                                            text = stringResource(R.string.email) + ": ",
                                            fontWeight = FontWeight.SemiBold,
                                            style = MaterialTheme.typography.body1,
                                            modifier = Modifier.weight(0.3f)
                                        )
                                        Spacer(modifier = Modifier.width(100.dp))
                                        TextField(
                                            value = newEmail,
                                            onValueChange = {
                                                newEmail = it
                                            },
                                            colors = TextFieldDefaults.textFieldColors(
                                                backgroundColor = MaterialTheme.colors.primary,
                                                disabledIndicatorColor = MaterialTheme.colors.primary,
                                                focusedIndicatorColor = MaterialTheme.colors.onPrimary
                                            ),
                                            modifier = Modifier.weight(0.7f)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                user?.data?.let { userData ->
                                    Button(
                                        onClick = {
                                            mainViewModel.saveProfileChange(
                                                newName.text,
                                                newEmail.text
                                            )
                                        },
                                        //TODO pake regex
                                        enabled =
                                        (newName.text != userData.name || newEmail.text != userData.email) &&
                                                (newName.text != "" || newEmail.text != ""),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = MaterialTheme.colors.secondary,
                                            disabledBackgroundColor = MaterialTheme.colors.surface,
                                        )
                                    ) {
                                        Text(text = stringResource(R.string.save_change))
                                        LaunchedEffect(status) {
                                            if (status) {
                                                mainViewModel.clearStatus()
                                                navBack()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(
                    Modifier.windowInsetsBottomHeight(
                        WindowInsets.systemBars
                    )
                )
            }
        }
    }
}

