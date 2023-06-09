@file:OptIn(ExperimentalMaterialApi::class)

package com.jetpack.kawanusaha.ui.pages.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.db.fav.DbFav
import com.jetpack.kawanusaha.main.LikeViewModel
import com.jetpack.kawanusaha.main.ListArticle
import com.jetpack.kawanusaha.main.ListArticleViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LikeScreen(
    navToArticle: (Int?) -> Unit,
    viewModel: LikeViewModel
){

    val list by viewModel.allData.observeAsState(listOf())
        Scaffold(topBar = { TopBarFavorite() }, modifier = Modifier.padding(15.dp)) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize(),
                ){
                    items(list, key = {item -> item.id}){
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
fun TopBarFavorite() {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Your Favorite Page",
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
            .height(220.dp),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 10.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.background
                ),
                contentScale = ContentScale.Fit,
                contentDescription = "Image",
                modifier = Modifier.size(130.dp,80.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Image Account"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Username",
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
                    text = "a few minutes ago",
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}