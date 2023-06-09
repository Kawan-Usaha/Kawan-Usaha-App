package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.MainViewModel

@Composable
fun UsahaDetailScreen(mainViewModel: MainViewModel, usahaId: Int, navBack: () -> Unit) {
    LaunchedEffect(mainViewModel) { mainViewModel.getUsahaDetail(usahaId) }
    val usaha by mainViewModel.usahaDetail.collectAsState(null)
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = {
                    Text(text = usaha?.data?.usaha_name ?: "UsahaName")
                },
                actions = {
                    IconButton(onClick = {
                        mainViewModel.deleteUsaha(id = usahaId)
                        navBack()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = stringResource(id = R.string.delete))
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        },
        modifier = Modifier.safeDrawingPadding()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = usaha?.data?.usaha_name ?: "Nama Usaha",
                    style = MaterialTheme.typography.h1
                )
            }
            items(usaha?.data?.tags?.size ?: 0) { index ->
                Text(
                    text = usaha?.data?.tags?.get(index)?.name ?: "Tag",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}