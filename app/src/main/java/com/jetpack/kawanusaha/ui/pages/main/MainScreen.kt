@file:OptIn(ExperimentalMaterialApi::class)

package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.data.ArticlesItem
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.NavFabButton
import com.jetpack.kawanusaha.ui.pages.TopBar
import com.jetpack.kawanusaha.ui.pages.shimmerBrush
import com.jetpack.kawanusaha.ui.theme.Typography

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    navToArticle: (Int) -> Unit,
    navToAddArticle: () -> Unit,
) {
    val articles: LazyPagingItems<ArticlesItem> =
        mainViewModel.getUserArticles().collectAsLazyPagingItems()
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = { TopBar {} },
            floatingActionButton = { NavFabButton(navToAddArticle) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
//                ChatBox(navToChat)

                Card(
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = MaterialTheme.colors.primary,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Category Section
                        SectionText(
                            text = "Category",
                            style = MaterialTheme.typography.h3,
                            modifier = Modifier.padding(15.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CategorySection()
                    }
                }
                Spacer(Modifier.height(20.dp))

                // Recommendation Articles Section
                SectionText(
                    text = "Recommendation Articles",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(15.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                ArticleSection(articles, navToArticle)
            }
        }
    }
}

@Composable
fun ChatBox(navToChat: () -> Unit) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(15.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
            )
//            .blur(15.dp)
    ) {
        Column(
            Modifier.padding(22.dp)
        ) {
            Text(
                "Chat With Your AI Mentor!",
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h3
            )
            Spacer(Modifier.height(12.dp))
            Card(
                onClick = navToChat,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, color = MaterialTheme.colors.secondary),
                backgroundColor = MaterialTheme.colors.primary,

                ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(30.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Send a Message",
                        Modifier.weight(1f),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Icon(
                        imageVector = Icons.Default.Send,
                        tint = MaterialTheme.colors.secondary,
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
        horizontalArrangement = Arrangement.spacedBy(20.dp),
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
        val showShimmer = remember { mutableStateOf(true) }
        AsyncImage(
            model = "URL DISINI",
            contentDescription = "Category",
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
            onSuccess = { showShimmer.value = false }
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Google", color = MaterialTheme.colors.onPrimary)
    }
}

@Composable
fun SectionText(text: String, style: TextStyle, modifier: Modifier) {
    Text(
        text = text,
        color = MaterialTheme.colors.onPrimary,
        style = style,
    )
}

@Composable
fun ArticleSection(articles: LazyPagingItems<ArticlesItem>, navToArticle: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        content = {
            items(articles.itemCount) { index ->
                articles[index]?.let {
                    ArticleItem(it, navToArticle)
                }
            }
        }
    )
}

@Composable
fun ArticleItem(articlesItem: ArticlesItem, navToArticle: (Int) -> Unit) {
    val title = articlesItem.title
    val id = articlesItem.id
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { navToArticle(id) }
    ) {
        val showShimmer = remember { mutableStateOf(true) }
        AsyncImage(
            model = "URL DISINI",
            contentDescription = "Articles",
            modifier = Modifier
                .height(100.dp)
                .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
            onSuccess = { showShimmer.value = false }
        )
        Text(text = title, modifier = Modifier.height(100.dp), style = Typography.h6)
    }
}