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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.onKeyboardVisible
import com.jetpack.kawanusaha.ui.pages.scrollToBottom
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


    LaunchedEffect(msg.value){
        scrollToBottom(lazyListState)
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
                val isKeyboardOpen by onKeyboardVisible()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)
                        .imePadding(),
                    state = lazyListState
                ) {
                    items(msg.value.size) { id ->
                        MessageItem(
                            messageText = msg.value[id].content,
                            time = simpleDateFormat.format(Calendar.getInstance().timeInMillis),
                            isOut = msg.value[id].role == "user",
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LaunchedEffect(isKeyboardOpen){
                            if(isKeyboardOpen){
                                scrollToBottom(lazyListState)
                            }
                        }
                    }
                    if (isLoading.value) {
                        item {
                            MessageItem(
                                messageText = if(newMsg == "") stringResource(R.string.connecting) else newMsg,
                                time = "",
                                isOut = false,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        item {
                            Spacer(modifier = Modifier)
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
                            Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.clear_cache))
                        }
                        OutlinedTextField(
                            placeholder = {
                                Text(text = stringResource(R.string.text_message))
                            },
                            value = text,
                            onValueChange = { text = it },
                            enabled = !isLoading.value,
                            shape = RoundedCornerShape(15.dp),
                            trailingIcon = {
                                if (text.text.isNotBlank()) {
                                    Icon(
                                        imageVector = Icons.Default.Send,
                                        contentDescription = stringResource(R.string.send),
                                        tint = MaterialTheme.colors.secondary,
                                        modifier = Modifier.clickable {
                                            scope.launch {
                                                mainViewModel.sendMsg(text.text)
                                                text = TextFieldValue("")
                                                scrollToBottom(lazyListState)
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
                                        scrollToBottom(lazyListState)
                                    }
                                }
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
                Text(text = stringResource(R.string.bot), fontWeight = FontWeight.SemiBold)
                Text(
                    text = stringResource(R.string.online),
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
