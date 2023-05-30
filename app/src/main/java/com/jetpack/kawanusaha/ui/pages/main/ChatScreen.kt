package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Device
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.kawanusaha.data.message_dummy
import java.text.SimpleDateFormat
import java.util.Locale

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun ChatScreen() {
    Text(text = "https://getstream.io/blog/android-jetpack-compose-chat-example/")
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(topBar = {TopBarSection()}) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ) {
                ChatSection(Modifier.weight(1f))
                MessageSection()
            }
        }

    }
}

@Composable
fun TopBarSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Column {
                Text(text = "Bot", fontWeight = FontWeight.SemiBold)
                Text(
                    text = "Online",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun ChatSection(
    modifier: Modifier = Modifier
) {
    val simpleDateFormat = SimpleDateFormat("h:mm a", Locale.US)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        reverseLayout = true
    ){
        items(message_dummy){chat ->
            MessageItem(
                messageText = chat.text, 
                time = simpleDateFormat.format(chat.time), 
                isOut = chat.isOut
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MessageItem(
    messageText: String?,
    time: String,
    isOut: Boolean
) {
    val botChatBubbleShape = RoundedCornerShape(0.dp,8.dp,8.dp,8.dp)
    val authorChatBubbleShape = RoundedCornerShape(8.dp,0.dp,8.dp,8.dp)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if(isOut) Alignment.End else Alignment.Start
    ) {
        if(messageText != null){
            if(messageText.isNotEmpty()){
                Box(
                    modifier = Modifier
                        .background(
                            if (isOut) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
                            shape = if (isOut) authorChatBubbleShape else botChatBubbleShape
                        )
                        .padding(
                            vertical = 8.dp,
                            horizontal = 16.dp
                        )
                ) {
                    Text(
                        text = messageText,
                        style = MaterialTheme.typography.body1)
                }
            }
        }
        Text(
            text = time,
            fontSize = 12.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun MessageSection() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 10.dp
    ) {
        OutlinedTextField(
            placeholder = {
                Text(text = "Text Message")
            },
            value = text,
            onValueChange = {text = it},
            shape = RoundedCornerShape(15.dp),
            trailingIcon = {
                if(text.text.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.clickable { }
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedLabelColor = MaterialTheme.colors.surface,
                focusedBorderColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.onPrimary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }

}