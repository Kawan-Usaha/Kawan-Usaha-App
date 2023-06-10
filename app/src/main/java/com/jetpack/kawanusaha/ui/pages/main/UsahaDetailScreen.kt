package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.main.MainViewModel

@Composable
fun UsahaDetailScreen (mainViewModel: MainViewModel, usahaId: Int, navBack: () -> Unit){
    LaunchedEffect(mainViewModel) { mainViewModel.getUsahaDetail(usahaId) }
    val usaha by mainViewModel.usahaDetail.collectAsState(null)
    val id = usaha?.data?.id
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(text = usaha?.data?.usaha_name ?: "UsahaName")
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        }
    ){innerPadding ->
        Column(Modifier.padding(innerPadding).safeDrawingPadding()) {
            Text(text = id.toString())
        }
    }


}