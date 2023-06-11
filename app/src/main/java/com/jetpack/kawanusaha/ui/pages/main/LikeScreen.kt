
package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.LikeViewModel

@Composable
fun LikeScreen(
    navToArticle: (Int?) -> Unit,
    viewModel: LikeViewModel
){
    val list by viewModel.allData.observeAsState(listOf())
    var search by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    Scaffold(topBar = { TopBarFavorite() }, modifier = Modifier.padding(15.dp).safeDrawingPadding()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(list, key = { item -> item.id }) {
                    ArticleList(
                        title = it.title,
                        description = it.content,
                        articleId = it.articleId,
                        navToArticle = navToArticle
                    )
                }
            }
        }
    }
}
@Composable
fun TopBarFavorite(
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.your_favorite_page),
            style = MaterialTheme.typography.h3,
            modifier = Modifier.weight(1f)
        )

    }
}

@Composable
fun ArticleList(
    title: String?,
    description: String?,
    articleId: Int?,
    navToArticle: (Int?) -> Unit,
) {
    Card(
        modifier = Modifier
            .clickable { navToArticle(articleId) }
            .fillMaxWidth()
            .height(150.dp),
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(15.dp)
    ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(32.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = stringResource(R.string.image_account)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.username),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Light
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h3
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.body1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.a_few_minutes_ago),
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
    }
}