package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.data.UsahaData
import com.jetpack.kawanusaha.data.UsahaResponse
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.NavFabButton
import com.jetpack.kawanusaha.ui.pages.TopBar
import com.jetpack.kawanusaha.ui.pages.shimmerBrush
import com.jetpack.kawanusaha.ui.theme.Typography

@Composable
fun UsahaScreen(mainViewModel: MainViewModel, navToAddUsaha: () -> Unit, navToUsahaDetail: (Int) -> Unit) {
    Scaffold(
        floatingActionButton = { NavFabButton(navToAddUsaha) },
        topBar = { TopBar {} }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SectionText(
                text = "Types",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(15.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            CategorySection()

            Spacer(Modifier.height(8.dp))
            // Recommendation Articles Section
            SectionText(
                text = "Usaha",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(15.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            CallUsahaLists(mainViewModel = mainViewModel)
            val data by mainViewModel.listUsaha.collectAsState(null)
            UsahaSection(data, navToUsahaDetail)
        }
    }
}

@Composable
fun UsahaSection(response: UsahaResponse?, navToUsahaDetail: (Int) -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.height(screenHeight.dp)
    ) {
        items(response?.data?.size ?: 0) {
            UsahaItem(usaha_name = response?.data?.get(it)!!, navToUsahaDetail)
        }
    }
}

@Composable
fun UsahaItem(usaha_name: UsahaData, navToUsahaDetail: (Int) -> Unit) {
    val title = usaha_name.usaha_name
    val id = usaha_name.id
    Card (modifier = Modifier.clickable{ navToUsahaDetail(id) }){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp),
        ) {
            val showShimmer = remember { mutableStateOf(true) }
            AsyncImage(
                model = "URL DISINI",
                contentDescription = "Foto Usaha",
                modifier = Modifier.height(300.dp).background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
                    onSuccess = {showShimmer.value = false}
            )
            Text(text = title, modifier = Modifier.height(100.dp), style = Typography.h5)
        }
    }
}

@Composable
fun CallUsahaLists(mainViewModel: MainViewModel) {
    LaunchedEffect(mainViewModel.listUsaha) {
        mainViewModel.getListUsaha()
    }
}