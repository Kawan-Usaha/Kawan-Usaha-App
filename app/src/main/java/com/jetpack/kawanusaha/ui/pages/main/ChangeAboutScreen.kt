package com.jetpack.kawanusaha.ui.pages.main

import android.graphics.*
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.MainViewModel

@Composable
fun ChangeAboutScreen(mainViewModel: MainViewModel, navBack: () -> Unit) {
    var newName by remember { mutableStateOf(TextFieldValue("")) }
    var newEmail by remember { mutableStateOf(TextFieldValue("")) }
    val status by mainViewModel.status.collectAsState(initial = false)

    var showAlertDialog by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){uri: Uri? ->
        imageUri = uri
    }

    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(text = "Profile")
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
                        bitmap.value.let { btm ->
                            val resources = context.resources
                            val bitmapValue: Bitmap? = btm
                            val defaultBitmap = BitmapFactory.decodeResource(resources, R.drawable.profile)
                            val imageBitmap = bitmapValue?.asImageBitmap() ?: defaultBitmap.asImageBitmap()
                            Image(
                                bitmap = imageBitmap,
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(200.dp)
                                    .padding(8.dp)
                                    .clip(CircleShape)
                            )
                            Icon(
                                imageVector = Icons.Default.Edit,
                                tint = Color.White,
                                contentDescription = "Edit Profile Picture",
                                modifier = Modifier
                                    .size(40.dp)
                                    .alpha(0.8f)
                                    .clickable {
                                        showAlertDialog = !showAlertDialog
                                    }
                            )
                            if (showAlertDialog) {
                                SelectImageAlertDialog(onDismiss = {
                                    showAlertDialog = !showAlertDialog
                                }, launcher)
                            }
                        }
                    }
                }
            }
            item{
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
                                    text = user?.data?.name!!.toString(),
                                    style = MaterialTheme.typography.h3
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    user?.data?.let { userData ->
                                        newName = TextFieldValue(userData.name)
                                        newEmail = TextFieldValue(userData.email)
                                        Text(
                                            text = "Account Id: ",
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
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Name: ",
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
                                            focusedIndicatorColor = MaterialTheme.colors.onPrimary
                                        ),
                                        modifier = Modifier.weight(0.7f)
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Email: ",
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
                                        enabled = (newName.text != userData.name || newEmail.text != userData.email) && (newName.text != "" || newEmail.text != ""),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = MaterialTheme.colors.secondary,
                                            disabledBackgroundColor = MaterialTheme.colors.surface,
                                        )
                                    ) {
                                        Text(text = "Save Change")
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

@Composable
fun SelectImageAlertDialog(
    onDismiss: () -> Unit,
    launcher: ManagedActivityResultLauncher<String, Uri?>,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(8.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                Text(
                    text = "Take from Camera",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                        }
                )
                Divider(color = MaterialTheme.colors.onPrimary)
                Text(
                    text = "Select from Gallery",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                )
            }
        }
    }
}