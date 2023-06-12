package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.data.ArticlesItem
import com.jetpack.kawanusaha.ui.pages.shimmerBrush
import com.jetpack.kawanusaha.ui.theme.Typography


@Composable
fun ArticleSection(articles: LazyPagingItems<ArticlesItem>, navToArticle: (Int) -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp * 3/4
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.height((screenHeight).dp),
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
    var isError by remember {
        mutableStateOf(false)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { navToArticle(id) }
    ) {
        val showShimmer = remember { mutableStateOf(true) }
        if (isError){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Failed to get Image",
                modifier = Modifier.height(100.dp))
        } else {
            AsyncImage(
                model = articlesItem.image,
                contentDescription = "Articles",
                modifier = Modifier
                    .height(100.dp)
                    .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
                onSuccess = { showShimmer.value = false },
                onError = { showShimmer.value = false; isError = true }
            )
        }
        Text(text = title, modifier = Modifier.height(100.dp), style = Typography.h6)
    }
}