package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.data.Tag
import com.jetpack.kawanusaha.main.MainViewModel


@Composable
fun AddUsahaScreen(mainViewModel: MainViewModel, navBack: () -> Unit) {
    var usahaName by remember { mutableStateOf(TextFieldValue("")) }
    var tag1 by remember { mutableStateOf("") }
    var tag2 by remember { mutableStateOf("") }
    var tag3 by remember { mutableStateOf("") }
    val status by mainViewModel.status.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Usaha") },
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Arrow Back"
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            val tagList = mutableListOf<Tag>()
                            if (tag1 != "-- None --") {
                                tagList.add(Tag(tag1))
                            }
                            if (tag2 != "-- None --") {
                                tagList.add(Tag(tag2))
                            }
                            if (tag3 != "-- None --") {
                                tagList.add(Tag(tag3))
                            }
                            mainViewModel.createUsaha(usahaName.text, 1, tagList)
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    ) {
                        Text(text = "Create", style = MaterialTheme.typography.body1)
                    }
                }
            )
        },
        modifier = Modifier.safeDrawingPadding()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .imePadding()
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Card(
                    Modifier.fillMaxSize(),
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        val customTextSelection = TextSelectionColors(
                            handleColor = MaterialTheme.colors.secondary,
                            backgroundColor = MaterialTheme.colors.background
                        )
                        // Title
                        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelection) {
                            TextField(
                                value = usahaName,
                                onValueChange = { usahaName = it },
                                textStyle = MaterialTheme.typography.h3,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MaterialTheme.colors.primary,
                                    cursorColor = MaterialTheme.colors.onPrimary,
                                    unfocusedIndicatorColor = MaterialTheme.colors.surface,
                                    focusedIndicatorColor = MaterialTheme.colors.secondary
                                ),
                                singleLine = true,
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.usaha_name),
                                        style = MaterialTheme.typography.h3
                                    )
                                }
                            )
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Top,
                            ) {
                                var textFilledSize by remember { mutableStateOf(Size.Zero) }
                                OutlinedTextField(
                                    value = tag1,
                                    onValueChange = { tag1 = it },
                                    modifier = Modifier
                                        .padding(vertical = 16.dp)
                                        .onGloballyPositioned { coordinates ->
                                            textFilledSize = coordinates.size.toSize()
                                        },
                                    label = { Text(text = stringResource(R.string.insert_tag)) },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        unfocusedBorderColor = MaterialTheme.colors.secondary,
                                        focusedBorderColor = MaterialTheme.colors.secondary,
                                        unfocusedLabelColor = MaterialTheme.colors.surface,
                                        focusedLabelColor = MaterialTheme.colors.secondary
                                    )
                                )

                                OutlinedTextField(
                                    value = tag2,
                                    onValueChange = { tag2 = it },
                                    modifier = Modifier
                                        .padding(vertical = 16.dp)
                                        .onGloballyPositioned { coordinates ->
                                            textFilledSize = coordinates.size.toSize()
                                        },
                                    label = { Text(text = stringResource(R.string.insert_tag)) },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        unfocusedBorderColor = MaterialTheme.colors.secondary,
                                        focusedBorderColor = MaterialTheme.colors.secondary,
                                        unfocusedLabelColor = MaterialTheme.colors.surface,
                                        focusedLabelColor = MaterialTheme.colors.secondary
                                    )
                                )
                                OutlinedTextField(
                                    value = tag3,
                                    onValueChange = { tag3 = it },
                                    modifier = Modifier
                                        .padding(vertical = 16.dp)
                                        .onGloballyPositioned { coordinates ->
                                            textFilledSize = coordinates.size.toSize()
                                        },
                                    label = { Text(text = stringResource(R.string.insert_tag)) },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        unfocusedBorderColor = MaterialTheme.colors.secondary,
                                        focusedBorderColor = MaterialTheme.colors.secondary,
                                        unfocusedLabelColor = MaterialTheme.colors.surface,
                                        focusedLabelColor = MaterialTheme.colors.secondary
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(status) {
        if (status) {
            mainViewModel.clearStatus()
            navBack()
        }
    }
}
