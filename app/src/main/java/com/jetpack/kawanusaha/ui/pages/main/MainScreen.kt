@file:OptIn(ExperimentalMaterialApi::class)

package com.jetpack.kawanusaha.ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.ui.theme.Typography

@Composable
fun MainScreen(navToChat: () -> Unit, navToArticle: (String) -> Unit, navToAbout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { navToAbout() }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Account"
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            ChatBox(navToChat)

            Card(
                shape = RoundedCornerShape(10.dp),
                backgroundColor = MaterialTheme.colors.onBackground,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Category Section
                    SectionText(text = "Category")
                    CategorySection()

                    // Recommendation Articles Section
                    SectionText(text = "Recommendation Articles")
                    ArticleSection(navToArticle)
                }
            }
        }
    }
}

@Composable
fun ChatBox(navToChat: () -> Unit) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
        modifier = Modifier.padding(20.dp)
    ) {
        Column(
            Modifier.padding(20.dp)
        ) {
            Text("Chat With Your AI Mentor", Modifier.padding(10.dp))
            Card(
                onClick = navToChat,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, color = MaterialTheme.colors.primary),
                backgroundColor = MaterialTheme.colors.background,

                ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(30.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Send a Message", Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send"
                    )
                }
            }
        }
    }
}

@Composable
fun CategorySection() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(20) {
            CategoryItem()
        }
    }
}

@Composable
fun CategoryItem() {
    //TODO: Change This
    Column {
        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape),
        )
        Text(text = "Google", color = MaterialTheme.colors.background)
    }
}

@Composable
fun SectionText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.background,
        style = Typography.h3,
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun ArticleSection(navToArticle: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(50) {
            ArticleItem(navToArticle)
        }
    }
}

@Composable
fun ArticleItem(navToArticle: (String) -> Unit) {
    val title = "Title"
    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable { navToArticle(title) }
                .padding(20.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.height(300.dp)
            )
            Text(text = title, modifier = Modifier.height(100.dp), style = Typography.h5)
        }
    }

}
