@file:OptIn(ExperimentalMaterial3Api::class)

package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.data.ArticlesItem
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.NavFabButton
import com.jetpack.kawanusaha.ui.pages.main.utils.CategorySection

@Composable
fun ExploreScreen(
    mainViewModel: MainViewModel,
    navToAddArticle: () -> Unit,
    navToArticle: (Int) -> Unit
) {
    var search by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val items = remember { mutableStateListOf("") }
    val articles = getData(mainViewModel = mainViewModel, searchText = search)
    Scaffold(
        floatingActionButton = { NavFabButton(navToAddArticle) },
        modifier = Modifier.fillMaxSize().safeDrawingPadding()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            // Search Section
            val customTextSelection = TextSelectionColors(
                handleColor = MaterialTheme.colors.secondary,
                backgroundColor = MaterialTheme.colors.background
            )
            CompositionLocalProvider(LocalTextSelectionColors provides customTextSelection) {
                SearchBar(
                    query = search,
                    onQueryChange = { search = it },
                    onSearch = {
                        items.add(search)
                        active = false
                    },
                    active = active,
                    onActiveChange = { active = it; search = ""},
                    colors = SearchBarDefaults.colors(MaterialTheme.colors.primary),
                    placeholder = { Text(stringResource(R.string.search)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_icon),
                            tint = MaterialTheme.colors.secondary
                        )
                    },
                    trailingIcon = {
                        if (active) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.close),
                                modifier = Modifier.clickable {
                                    if (search.isNotEmpty()) {
                                        search = ""
                                    } else {
                                        active = false
                                    }
                                }
                            )
                        }
                    },
                ) {
                    items.forEach {
                        if (it.isNotEmpty()){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .clickable { search = it }.fillMaxWidth().height(60.dp).padding(horizontal = 20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = stringResource(R.string.history_icon),
                                    modifier = Modifier.padding(end = 20.dp)
                                )
                                Text(text = it)
                            }
                        }
                    }
                }

                // Category Section
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Card(
                        backgroundColor = MaterialTheme.colors.background,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            CategorySection()
                        }
                    }
                }

                // Recommendation Articles Section
                ArticleSection(articles, navToArticle)
            }
        }
    }
}

@Composable
fun getData(mainViewModel: MainViewModel, searchText: String): LazyPagingItems<ArticlesItem> {
    return if (searchText.isNotEmpty()) {
        mainViewModel.searchAllArticle(searchText).collectAsLazyPagingItems()
    } else {
        mainViewModel.getAllArticles().collectAsLazyPagingItems()
    }
}


