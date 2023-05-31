package com.jetpack.kawanusaha.ui.pages.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.data.Tag
import com.jetpack.kawanusaha.main.MainViewModel


@Composable
fun AddUsahaScreen(mainViewModel: MainViewModel) {
    var usahaName by remember { mutableStateOf(TextFieldValue("")) }
    var type by remember { mutableStateOf(TextFieldValue("")) }
    var tag1 by remember { mutableStateOf(TextFieldValue("")) }
    var tag2 by remember { mutableStateOf(TextFieldValue("")) }
    var tag3 by remember { mutableStateOf(TextFieldValue("")) }
    Column {
        SectionText(
            text = "Ubah Usaha",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(15.dp)
        )

        OutlinedTextField(
            value = usahaName,
            onValueChange = { usahaName = it },
            label = { Text(text = "Usaha Name") }
        )

        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Type") }
        )
        OutlinedTextField(
            value = tag1,
            onValueChange = {
                tag1 = it
            },
            label = { Text(text = "Tag 1") }
        )

        OutlinedTextField(
            value = tag2,
            onValueChange = {
                tag2 = it
            },
            label = { Text(text = "Tag 2") }
        )

        OutlinedTextField(
            value = tag3,
            onValueChange = {
                tag3 = it
            },
            label = { Text(text = "Tag 3") }
        )

        Button(onClick = {
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
        }) {
            Text(text = "Create")
        }
    }
}