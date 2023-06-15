package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.data.ArticlesItem
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.TopBar
import com.jetpack.kawanusaha.ui.pages.shimmerBrush

@Composable
fun LikeScreen(
    navToArticle: (Int?) -> Unit,
    mainViewModel: MainViewModel,
) {
    mainViewModel.getFavourite()
    val list by mainViewModel.favouriteList.collectAsState()
    Scaffold(
        topBar = { TopBar {} }, modifier = Modifier
            .padding(15.dp)
            .safeDrawingPadding()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Your Favorite",
                style = MaterialTheme.typography.h1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(list?.size ?: 0) {
                    ArticleList(
                        article = list?.get(it)!!,
                        navToArticle = navToArticle,
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleList(
    article: ArticlesItem,
    navToArticle: (Int?) -> Unit,
) {
    Card(
        modifier = Modifier
            .clickable { navToArticle(article.id) }
            .fillMaxWidth()
            .height(120.dp),
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(horizontal = 25.dp)
        ) {
            val showShimmer = remember { mutableStateOf(true) }
            AsyncImage(
                model = article.image,
                contentDescription = "Articles",
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = showShimmer.value
                        ),
                    )
                    .clip(CircleShape)
                    .border(BorderStroke(0.5.dp, MaterialTheme.colors.secondary)),
                onSuccess = { showShimmer.value = false },
                onError = { showShimmer.value = false },
                contentScale = ContentScale.Crop
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(32.dp)
                    .weight(0.7f)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.h3,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true
                )
            }
        }
    }
}