package com.jetpack.kawanusaha.ui.pages.main

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.db.fav.DbFav
import com.jetpack.kawanusaha.main.LikeViewModel
import com.jetpack.kawanusaha.main.MainViewModel

@Composable
fun ArticleScreen(mainViewModel: MainViewModel, viewModel: LikeViewModel, articleId: Int, navBack: () -> Unit) {
    LaunchedEffect(mainViewModel){
        mainViewModel.getArticleDetail(id = articleId)
    }
    val article by mainViewModel.articleDetail.collectAsState(null)
    val context = LocalContext.current
    val text = "Share Text"

    var isFavorite by rememberSaveable { mutableStateOf(false) }
    val getById by viewModel.getAllDataById(articleId).observeAsState()

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
                        val favoriteMap = DbFav(
                                title = article?.title,
                                content = article?.content,
                                createdAt = article?.createdAt,
                            articleId = articleId
                            )
                        IconButton(
                            onClick = {
                                isFavorite = !isFavorite
                                if (getById?.isEmpty() == true) {
                                        viewModel.insert(favoriteMap)
                                        Icons.Default.Favorite
                                        Toast.makeText(
                                            context,
                                            "Added to favorite",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        viewModel.deleteById(articleId)
                                        Icons.Default.FavoriteBorder
                                        Toast.makeText(
                                            context,
                                            "Removed from favorite",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                }

                            }) {
                            Icon(
                                imageVector = if(getById?.isNotEmpty() == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                tint = MaterialTheme.colors.secondary,
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
                                tint = MaterialTheme.colors.secondary,
                                contentDescription = "Share"
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
                    AsyncImage(
                        model = "https://www.asiamediajournal.com/wp-content/uploads/2022/11/Default-PFP-1200x1200.jpg",
                        contentDescription = "Article Picture",
                        placeholder = painterResource(id = R.drawable.profile),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 500.dp, height = 300.dp)
                            .padding(8.dp)
                    )
                }
                item {
                    Text(text = article?.content ?: "Content", style = MaterialTheme.typography.h3, modifier = Modifier.padding(8.dp))
                }
                item {
                    Text(text = article?.content ?: "Content", style = MaterialTheme.typography.body1 ,modifier = Modifier.padding(8.dp))
                }
            }
        }
}
