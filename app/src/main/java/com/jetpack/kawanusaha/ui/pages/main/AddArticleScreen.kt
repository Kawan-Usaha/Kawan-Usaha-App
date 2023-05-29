package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.main.MainViewModel

@Composable
fun AddArticleScreen(mainViewModel: MainViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var title by remember { mutableStateOf(TextFieldValue("")) }
        var content by remember { mutableStateOf(TextFieldValue("")) }
        Text(text = "Write Articles")
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Article Title") }
        )
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text(text = "Content") },
            modifier = Modifier.height(200.dp)
        )
        Button(
            onClick = {
                // Nanti bisa ambil gambar
            }
        ) {
            Text(text = "Add Picture")
        }

        Button(
            onClick = {
                mainViewModel.createArticle(title = title.text, content = content.text)
            }
        ) {
            Text(text = "Submit")
        }
    }
}

