package com.jetpack.kawanusaha.ui.pages.main

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.MainViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun ArticleScreen(mainViewModel: MainViewModel, articleId: Int, navBack: () -> Unit) {
    LaunchedEffect(mainViewModel) {
        mainViewModel.getArticleDetail(id = articleId)
    }
    val article by mainViewModel.articleDetail.collectAsState(null)
    val context = LocalContext.current
    val addToFavorite = stringResource(R.string.added_to_favorite)
    val removeFromFavorite = stringResource(R.string.removed_from_favorite)
    val shareVia = stringResource(R.string.share_via)

    var isFavorite by remember { mutableStateOf(article?.favorite ?: false) }

    LaunchedEffect( article?.favorite ){
        isFavorite = article?.favorite ?: false
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = { Text(text = "") },
                actions = {
                    IconButton(
                        onClick = {
                            if (isFavorite) {
                                mainViewModel.deleteFavourite(id = articleId)
                                Toast.makeText(
                                    context,
                                    removeFromFavorite,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                mainViewModel.setFavourite(id = articleId)
                                Toast.makeText(
                                    context,
                                    addToFavorite,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            isFavorite = !isFavorite
                        }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            tint = MaterialTheme.colors.secondary,
                            contentDescription = stringResource(R.string.like)
                        )
                    }
                    // Share Button
                    val text = stringResource(R.string.coming_soon)
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            intent.putExtra(Intent.EXTRA_TEXT, text)
                            val chooserIntent = Intent.createChooser(intent, shareVia)
                            context.startActivity(chooserIntent)
                        }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            tint = MaterialTheme.colors.secondary,
                            contentDescription = stringResource(R.string.share)
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                modifier = Modifier.padding(8.dp),
            )
        },
        modifier = Modifier.safeDrawingPadding()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp),

            horizontalAlignment = Alignment.Start
        ) {
            item {
                Text(
                    text = article?.title ?: "Title",
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(8.dp)
                )
            }
            item {
                AsyncImage(
                    model = article?.image,
                    contentDescription = "Article Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            item {
                Text(
                    text = article?.content ?: "Content",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
