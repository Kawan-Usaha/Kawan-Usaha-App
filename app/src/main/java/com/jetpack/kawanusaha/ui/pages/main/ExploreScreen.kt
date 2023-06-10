@file:OptIn(ExperimentalMaterial3Api::class)

package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        modifier = Modifier.fillMaxSize().safeDrawingPadding()
    ) { innerPadding ->
        var searchText by remember { mutableStateOf(TextFieldValue("")) }
        var search by remember { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }
        var items = remember {mutableStateListOf(
            ""
        )}
        val articles = getData(mainViewModel = mainViewModel, searchText = search)

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
                        active = false },
                    active = active,
                    onActiveChange = { active = it },
                    colors = SearchBarDefaults.colors(MaterialTheme.colors.primary),
                    placeholder = { Text("Search") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colors.secondary
                        )
                    },
                    trailingIcon = {
                        if (active) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "close",
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
                    items.forEach{
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, bottom = 18.dp)
                                .clickable { search = it }
                        ){
                            if(it.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = "History Icon",
                                    modifier = Modifier
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = it)
                        }
                    }
                }
                Spacer(Modifier.height(15.dp))
                // Category Section
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                ){
                    Card(
                        backgroundColor = MaterialTheme.colors.primary,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            SectionText(
                                text = "Category",
                                style = MaterialTheme.typography.h3,
                                modifier = Modifier.padding(15.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            CategorySection()
                            Spacer(Modifier.height(15.dp))
                        }
                    }
                }

                Spacer(Modifier.height(15.dp))
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
}

@Composable
fun getData(mainViewModel: MainViewModel, searchText: String) : LazyPagingItems<ArticlesItem> {
    return if (searchText.isNotEmpty()){
        mainViewModel.searchAllArticle(searchText).collectAsLazyPagingItems()
    } else {
        mainViewModel.getAllArticles().collectAsLazyPagingItems()
    }
}


