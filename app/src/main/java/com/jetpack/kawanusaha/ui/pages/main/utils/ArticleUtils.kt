package com.jetpack.kawanusaha.ui.pages.main.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.data.ArticlesItem
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.shimmerBrush
import com.jetpack.kawanusaha.ui.theme.Typography

@Composable
fun ArticleSection(
    articles: LazyPagingItems<ArticlesItem>,
    navToArticle: (Int) -> Unit,
    mainViewModel: MainViewModel? = null
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp * 3 / 4
    LazyVerticalGrid(
        columns = GridCells.Fixed(2) ,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .height((screenHeight).dp)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        content = {
            items(articles.itemCount) { index ->
                articles[index]?.let {
                    if (mainViewModel != null) {
                        UserArticleItem(
                            articlesItem = it,
                            navToArticle = navToArticle,
                            mainViewModel = mainViewModel
                        )
                    } else {
                        ArticleItem(articlesItem = it, navToArticle = navToArticle)
                    }
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
            .padding(bottom = 10.dp)
    ) {
        val showShimmer = remember { mutableStateOf(true) }
        if (isError) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Failed to get Image",
                modifier = Modifier.height(200.dp)
            )
        } else {
            AsyncImage(
                model = articlesItem.image,
                contentDescription = "Articles",
                modifier = Modifier
                    .height(200.dp)
                    .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
                onSuccess = { showShimmer.value = false },
                contentScale = ContentScale.FillWidth,
                onError = { showShimmer.value = false; isError = true }
            )
        }
        Text(text = title, style = Typography.h6, maxLines = 2, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun UserArticleItem(
    articlesItem: ArticlesItem,
    navToArticle: (Int) -> Unit,
    mainViewModel: MainViewModel
) {
    val title = articlesItem.title
    val id = articlesItem.id
    var isError by remember {
        mutableStateOf(false)
    }
    val deleted by remember { mutableStateOf(false) }

    if(!deleted){
        Box{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { navToArticle(id) }
                    .padding(bottom = 10.dp)
            ) {
                val showShimmer = remember { mutableStateOf(true) }
                if (isError) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Failed to get Image",
                        modifier = Modifier.height(200.dp)
                    )
                } else {
                    AsyncImage(
                        model = articlesItem.image,
                        contentDescription = "Articles",
                        modifier = Modifier
                            .height(200.dp)
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = showShimmer.value
                                )
                            ),
                        onSuccess = { showShimmer.value = false },
                        contentScale = ContentScale.FillWidth,
                        onError = { showShimmer.value = false; isError = true }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = Typography.h6,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
//                    IconButton(
//                        onClick = {
//                            mainViewModel.deleteArticle(id)
//                            deleted
//                        }
//                    ) {
//                        Icon(
//                            Icons.Default.Delete,
//                            contentDescription = stringResource(id = R.string.delete)
//                        )
//                    }
                    DropDownArticle(mainViewModel = mainViewModel, id = id )
                }

            }
        }
    }
}

@Composable
fun DropDownArticle(mainViewModel: MainViewModel, id:Int) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .wrapContentSize(Alignment.BottomEnd)

    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"

            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(MaterialTheme.colors.primary)
        ) {
            DropdownMenuItem(onClick = {
                mainViewModel.deleteArticle(id)
            }) {
                Text(text = "Delete")
            }
            DropdownMenuItem(
                onClick = {}
            ) {
                Text(text = "Publish")

            }
            DropdownMenuItem(
                onClick = {}
            ) {
                Text(text = "UnPublish")
            }
        }


    }
}