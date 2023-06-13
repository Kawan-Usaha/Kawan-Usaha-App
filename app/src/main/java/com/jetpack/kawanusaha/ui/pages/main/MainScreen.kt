package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.data.ArticlesItem
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.NavFabButton
import com.jetpack.kawanusaha.ui.pages.SectionText
import com.jetpack.kawanusaha.ui.pages.TopBar
import com.jetpack.kawanusaha.ui.pages.main.utils.ArticleItem
import com.jetpack.kawanusaha.ui.pages.main.utils.CategorySection
import com.jetpack.kawanusaha.ui.pages.shimmerBrush
import com.jetpack.kawanusaha.ui.theme.Typography

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    navToArticle: (Int) -> Unit,
    navToAddArticle: () -> Unit,
) {
    val articles = mainViewModel.getAllArticles().collectAsLazyPagingItems()
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
                MainArticleSection(mainViewModel, articles, navToArticle)
            }
        }
    }
}

@Composable
fun MainArticleSection(mainViewModel: MainViewModel, articles: LazyPagingItems<ArticlesItem>, navToArticle: (Int) -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    LazyColumn(
        modifier = Modifier.height((screenHeight).dp).fillMaxWidth(),
        content = {
            item{
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
        modifier = Modifier
            .clickable { navToArticle(id) }
            .padding(bottom = 30.dp)
    ) {
        val showShimmer = remember { mutableStateOf(true) }
        Divider()
        if (!isError){
            AsyncImage(
                model = articlesItem.image,
                contentDescription = "Articles",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
                onSuccess = { showShimmer.value = false },
                onError = { showShimmer.value = false; isError = true }
            )
        }
        Text(text = title, modifier = Modifier.padding(vertical = 5.dp), style = Typography.h6)

    }
}