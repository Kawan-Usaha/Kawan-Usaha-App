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
                                tag1 = TagDropDown(mainViewModel = mainViewModel, tag2, tag3)
                                tag2 = TagDropDown(mainViewModel = mainViewModel, tag1, tag3)
                                tag3 = TagDropDown(mainViewModel = mainViewModel, tag1, tag2)
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


@Composable
fun TagDropDown(
    mainViewModel: MainViewModel,
    otherTag1: String,
    otherTag2: String,
) : String {
    var expanded by remember { mutableStateOf(false) }
    val list by mainViewModel.tagList.collectAsState(null)
    var selectedItem by remember { mutableStateOf("-- None --") }
    var textFilledSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(
        modifier = Modifier
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .onGloballyPositioned { coordinates ->
                    textFilledSize = coordinates.size.toSize()
                },
            label = { Text(text = "Select Tag") },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            readOnly = true,
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                expanded = !expanded
                            }
                        }
                    }
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedLabelColor = MaterialTheme.colors.surface,
                focusedLabelColor = MaterialTheme.colors.secondary
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFilledSize.width.toDp() })
                .background(MaterialTheme.colors.primary)
        ) {
            DropdownMenuItem(onClick = {
                selectedItem = "-- None --"
                expanded = false
            }) {
                Text(text = "-- None --")
            }
            list?.forEach { tag ->
                if (tag.name != otherTag1 && tag.name != otherTag2) {
                    DropdownMenuItem(onClick = {
                        selectedItem = tag.name
                        expanded = false
                    }) {
                        Text(text = tag.name)
                    }
                }
            }
        }
    }
    return selectedItem
}