package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.kawanusaha.main.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatScreen(mainViewModel: MainViewModel) {
    val msg = mainViewModel.llmResponse.collectAsState(initial = emptyList())
    val newMsg by mainViewModel.stringResponse.collectAsState("")
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.US)
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val isLoading = mainViewModel.isLoading.collectAsState(false)

    suspend fun scrollToBottom() {
        lazyListState.animateScrollToItem(lazyListState.layoutInfo.totalItemsCount)
    }

    LaunchedEffect(msg.value){
        scrollToBottom()
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Scaffold(topBar = { TopBarSection() }) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f),
                    state = lazyListState
                ) {
                    items(msg.value.size) { id ->
                        MessageItem(
                            messageText = msg.value[id].content,
                            time = simpleDateFormat.format(Calendar.getInstance().timeInMillis),
                            isOut = msg.value[id].role == "user",
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    if (isLoading.value) {
                        item {
                            MessageItem(
                                messageText = if(newMsg == "") "Connecting" else newMsg,
                                time = "",
                                isOut = false,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    backgroundColor = MaterialTheme.colors.primary,
                    elevation = 10.dp,
                ) {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            mainViewModel.clearCache()
                        }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear Cache")
                        }
                        OutlinedTextField(
                            placeholder = {
                                Text(text = "Text Message")
                            },
                            value = text,
                            onValueChange = { text = it },
                            enabled = !isLoading.value,
                            shape = RoundedCornerShape(15.dp),
                            trailingIcon = {
                                if (text.text.isNotBlank()) {
                                    Icon(
                                        imageVector = Icons.Default.Send,
                                        contentDescription = "Send",
                                        tint = MaterialTheme.colors.secondary,
                                        modifier = Modifier.clickable {
                                            scope.launch {
                                                mainViewModel.sendMsg(text.text)
                                                text = TextFieldValue("")
                                                scrollToBottom()
                                            }
                                        }
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
                                .clickable {
                                    scope.launch {
                                        scrollToBottom()
                                    }
                                }
                        )
                        Spacer(
                            Modifier.windowInsetsBottomHeight(
                                WindowInsets.systemBars
                            )
                        )
                    }
                }
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
 
            Spacer(modifier = Modifier.width(5.dp))
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
fun MessageItem(
    messageText: String?,
    time: String,
    isOut: Boolean,
) {
    val botChatBubbleShape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)
    val authorChatBubbleShape = RoundedCornerShape(8.dp, 0.dp, 8.dp, 8.dp)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isOut) Alignment.End else Alignment.Start
    ) {
        if (messageText != null) {
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
                    style = MaterialTheme.typography.body1
                )
            }
        }
        Text(
            text = time,
            fontSize = 12.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}
