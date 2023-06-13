package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.data.Tag
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.SectionText


@Composable
fun AddUsahaScreen(mainViewModel: MainViewModel, navBack: () -> Unit) {
    var usahaName by remember { mutableStateOf(TextFieldValue("")) }
    var type by remember { mutableStateOf(TextFieldValue("")) }
    var tag1 by remember { mutableStateOf(TextFieldValue("")) }
    var tag2 by remember { mutableStateOf(TextFieldValue("")) }
    var tag3 by remember { mutableStateOf(TextFieldValue("")) }

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
                            if(tag1.text.isNotBlank()){
                                tagList.add(Tag(tag1.text))
                            }
                            if(tag2.text.isNotBlank()){
                                tagList.add(Tag(tag2.text))
                            }
                            if(tag3.text.isNotBlank()){
                                tagList.add(Tag(tag3.text))
                            }
                            mainViewModel.createUsaha(usahaName.text, type.text.toInt(), tagList)
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
    ){innerPadding ->
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
                                    unfocusedIndicatorColor = MaterialTheme.colors.primary,
                                    focusedIndicatorColor = MaterialTheme.colors.primary
                                ),
                                singleLine = true,
                                placeholder = { Text(text = stringResource(R.string.usaha_name), style = MaterialTheme.typography.h3) }
                            )
                            TextField(
                                value = type,
                                onValueChange = { type = it },
                                textStyle = MaterialTheme.typography.body1,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MaterialTheme.colors.primary,
                                    cursorColor = MaterialTheme.colors.onPrimary,
                                    unfocusedIndicatorColor = MaterialTheme.colors.primary,
                                    focusedIndicatorColor = MaterialTheme.colors.primary
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                placeholder = {
                                    Text(
                                        text = "Type of Usaha",
                                        color = MaterialTheme.colors.surface,
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            )
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ){
                                TextField(
                                    value = tag1,
                                    onValueChange = { tag1 = it },
                                    textStyle = MaterialTheme.typography.body1,
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = MaterialTheme.colors.primary,
                                        cursorColor = MaterialTheme.colors.onPrimary,
                                        unfocusedIndicatorColor = MaterialTheme.colors.primary,
                                        focusedIndicatorColor = MaterialTheme.colors.primary
                                    ),
                                    placeholder = {
                                        Text(
                                            text = "Tag 1",
                                            color = MaterialTheme.colors.surface,
                                            style = MaterialTheme.typography.body1
                                        )
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                TextField(
                                    value = tag2,
                                    onValueChange = { tag2 = it },
                                    textStyle = MaterialTheme.typography.body1,
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = MaterialTheme.colors.primary,
                                        cursorColor = MaterialTheme.colors.onPrimary,
                                        unfocusedIndicatorColor = MaterialTheme.colors.primary,
                                        focusedIndicatorColor = MaterialTheme.colors.primary
                                    ),
                                    placeholder = {
                                        Text(
                                            text = "Tag 2",
                                            color = MaterialTheme.colors.surface,
                                            style = MaterialTheme.typography.body1
                                        )
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                TextField(
                                    value = tag3,
                                    onValueChange = { tag3 = it },
                                    textStyle = MaterialTheme.typography.body1,
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = MaterialTheme.colors.primary,
                                        cursorColor = MaterialTheme.colors.onPrimary,
                                        unfocusedIndicatorColor = MaterialTheme.colors.primary,
                                        focusedIndicatorColor = MaterialTheme.colors.primary
                                    ),
                                    placeholder = {
                                        Text(
                                            text = "Tag 3",
                                            color = MaterialTheme.colors.surface,
                                            style = MaterialTheme.typography.body1
                                        )
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}