package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.NavFabButton
import com.jetpack.kawanusaha.ui.pages.SectionText
import com.jetpack.kawanusaha.ui.pages.TopBar
import com.jetpack.kawanusaha.ui.pages.main.utils.CategorySection

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

                // TODO Make this only AI Generated and recommender
                ArticleSection(articles, navToArticle)
            }
        }
    }
}
