package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.NavFabButton
import com.jetpack.kawanusaha.ui.pages.SectionText
import com.jetpack.kawanusaha.ui.pages.TopBar
import com.jetpack.kawanusaha.ui.pages.main.utils.CallUsahaLists
import com.jetpack.kawanusaha.ui.pages.main.utils.CategorySection
import com.jetpack.kawanusaha.ui.pages.main.utils.UsahaSection

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
            CategorySection(mainViewModel)

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
