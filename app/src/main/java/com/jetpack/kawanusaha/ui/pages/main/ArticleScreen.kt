package com.jetpack.kawanusaha.ui.pages.main

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.MainViewModel

@Composable
fun ArticleScreen(mainViewModel: MainViewModel, articleId: Int, navBack: () -> Unit) {
    LaunchedEffect(mainViewModel){
        mainViewModel.getArticleDetail(id = articleId)
    }
    val article by mainViewModel.articleDetail.collectAsState(null)
    val context = LocalContext.current
    val text = "Share Text"
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(text = article?.title ?: "Title")
                },
                actions = {
                    // Like Button
                    var likeButton = Icons.Default.FavoriteBorder
                    IconButton(
                        onClick = {
                            likeButton = if (likeButton == Icons.Default.FavoriteBorder) {
                                Icons.Default.Favorite
                                // TODO add item to favourite
                            } else {
                                Icons.Default.FavoriteBorder
                                // TODO remove item from favourite
                            }
                        }) {
                        Icon(
                            imageVector = likeButton,
                            contentDescription = "Like"
                        )
                    }
                    // Share Button
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            intent.putExtra(Intent.EXTRA_TEXT, text)
                            val chooserIntent = Intent.createChooser(intent, "Share via")
                            context.startActivity(chooserIntent)
                        }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AsyncImage(
                    model = "https://www.asiamediajournal.com/wp-content/uploads/2022/11/Default-PFP-1200x1200.jpg",
                    contentDescription = "Article Picture",
                    placeholder = painterResource(id = R.drawable.profile),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(500.dp)
                        .padding(8.dp)
                )
            }
            item {
                Text(text = article?.content ?: "Content")
            }
        }
    }
}