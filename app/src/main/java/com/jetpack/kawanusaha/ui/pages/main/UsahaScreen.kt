package com.jetpack.kawanusaha.ui.pages.main

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.data.UsahaData
import com.jetpack.kawanusaha.data.UsahaResponse
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.NavFabButton
import com.jetpack.kawanusaha.ui.pages.SectionText
import com.jetpack.kawanusaha.ui.pages.TopBar
import com.jetpack.kawanusaha.ui.pages.main.utils.CategorySection
import com.jetpack.kawanusaha.ui.pages.shimmerBrush
import com.jetpack.kawanusaha.ui.theme.Typography

@Composable
fun UsahaScreen(
    mainViewModel: MainViewModel,
    navToAddUsaha: () -> Unit,
    navToUsahaDetail: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = { NavFabButton(navToAddUsaha) },
        topBar = { TopBar {} },
        modifier = Modifier.safeDrawingPadding()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SectionText(
                text = stringResource(R.string.types),
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(15.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            CategorySection()

            Spacer(Modifier.height(8.dp))
            // Recommendation Articles Section
            SectionText(
                text = stringResource(R.string.usaha),
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
