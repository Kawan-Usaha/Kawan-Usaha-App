
package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.LikeViewModel
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.shimmerBrush

@Composable
fun LikeScreen(
    navToArticle: (Int?) -> Unit,
    viewModel: LikeViewModel,
    mainViewModel: MainViewModel,
){
    val list by mainViewModel.favouriteList.collectAsState()
    var search by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    Scaffold(topBar = { TopBarFavorite() }, modifier = Modifier
        .padding(15.dp)
        .safeDrawingPadding()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 65.dp),
                shape = RoundedCornerShape(25.dp)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(25.dp)
                ) {
                    val user by mainViewModel.userProfile.collectAsState(initial = null)
                    val showShimmer = remember { mutableStateOf(true) }
                    AsyncImage(
                        model = "https://www.asiamediajournal.com/wp-content/uploads/2022/11/Default-PFP-1200x1200.jpg",
                        contentDescription = "Avatar User",
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = showShimmer.value
                                ),
                            )
                            .clip(CircleShape)
                            .border(BorderStroke(0.5.dp, MaterialTheme.colors.secondary)),
                        onSuccess = { showShimmer.value = false },
                        onError = { showShimmer.value = false},
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = user?.data?.name.toString(),
                        style = MaterialTheme.typography.h2,
                        fontWeight = FontWeight.Light
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(list?.size ?: 0) {
                    ArticleList(
                        title = list?.get(it)?.title,
                        articleId = list?.get(it)?.id,
                        image = list?.get(it)?.image,
                        navToArticle = navToArticle,
                        mainViewModel = mainViewModel
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
    articleId: Int?,
    image: String?,
    navToArticle: (Int?) -> Unit,
    mainViewModel: MainViewModel,
) {
    Card(
        modifier = Modifier
            .clickable { navToArticle(articleId) }
            .fillMaxWidth()
            .height(150.dp),
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(15.dp)
    ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(horizontal = 25.dp)
            ){
                val showShimmer = remember { mutableStateOf(true) }
                if (image == null){
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Failed to get Image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    AsyncImage(
                        model = image,
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
                        onError = { showShimmer.value = false},
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(32.dp)
                        .weight(0.7f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
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
}