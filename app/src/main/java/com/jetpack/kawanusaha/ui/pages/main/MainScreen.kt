package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.data.ArticlesItem
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.NavFabButton
import com.jetpack.kawanusaha.ui.pages.SectionText
import com.jetpack.kawanusaha.ui.pages.TopBar
import com.jetpack.kawanusaha.ui.pages.main.utils.CategorySection
import com.jetpack.kawanusaha.ui.pages.shimmerBrush
import com.jetpack.kawanusaha.ui.theme.Typography

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    navToArticle: (Int) -> Unit,
    navToAddArticle: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Scaffold(
            topBar = { TopBar {} },
            floatingActionButton = { NavFabButton(navToAddArticle) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                MainArticleSection(mainViewModel, navToArticle)
            }
        }
    }
}

@Composable
fun MainArticleSection(
    mainViewModel: MainViewModel,
    navToArticle: (Int) -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val category by mainViewModel.selectedCategory.collectAsState()
    val articles = mainViewModel.filterAllArticle("", category).collectAsLazyPagingItems()
    LazyColumn(
        modifier = Modifier
            .height((screenHeight).dp)
            .fillMaxWidth(),
        content = {
            item {
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
                            text = stringResource(R.string.category),
                            style = MaterialTheme.typography.h3,
                            modifier = Modifier.padding(15.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CategorySection(mainViewModel)
                    }
                }
                Spacer(Modifier.height(20.dp))
            }
            items(articles.itemCount) { index ->
                articles[index]?.let {
                    MainArticleItem(it, navToArticle)
                }
            }
        }
    )
}


@Composable
fun MainArticleItem(articlesItem: ArticlesItem, navToArticle: (Int) -> Unit) {
    val title = articlesItem.title
    val id = articlesItem.id
    var isError by remember {
        mutableStateOf(false)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { navToArticle(id) }
            .padding(bottom = 30.dp, start = 16.dp, end = 16.dp)
    ) {
        val showShimmer = remember { mutableStateOf(true) }
        if (isError) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Failed to get Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(350.dp)

            )
        } else {
            AsyncImage(
                model = articlesItem.image,
                contentDescription = "Articles",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(350.dp)
                    .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
                contentScale = ContentScale.Crop,
                onSuccess = { showShimmer.value = false },
                onError = { showShimmer.value = false; isError = true }
            )
        }
        Text(text = title, modifier = Modifier.padding(vertical = 5.dp), style = Typography.h6)

    }
}