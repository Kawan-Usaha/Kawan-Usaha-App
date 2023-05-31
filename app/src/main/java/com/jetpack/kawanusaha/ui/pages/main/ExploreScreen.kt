package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jetpack.kawanusaha.data.ArticlesItem
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.NavFabButton
import com.jetpack.kawanusaha.ui.pages.TopBar

@Composable
fun ExploreScreen (
    mainViewModel: MainViewModel,
    navToAddArticle: () -> Unit,
    navToArticle: (Int) -> Unit
) {
    Scaffold(
        topBar = { TopBar {} },
        floatingActionButton = { NavFabButton(navToAddArticle) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        var searchText by remember { mutableStateOf(TextFieldValue("")) }
        val articles = getData(mainViewModel = mainViewModel, searchText = searchText.text)
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            // Search Section
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                label = { Text(text = "Search") }
            )
            // Category Section
            SectionText(
                text = "Category",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(15.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            CategorySection()
            Spacer(Modifier.height(8.dp))

            // Recommendation Articles Section
            SectionText(
                text = "Recommendation Articles",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(15.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            ArticleSection(articles, navToArticle)
        }
    }
}

@Composable
fun getData(mainViewModel: MainViewModel, searchText: String) : LazyPagingItems<ArticlesItem> {
    return if (searchText.isNotEmpty()){
        mainViewModel.searchAllArticle(searchText).collectAsLazyPagingItems()
    } else {
        mainViewModel.getAllArticles().collectAsLazyPagingItems()
    }
}


