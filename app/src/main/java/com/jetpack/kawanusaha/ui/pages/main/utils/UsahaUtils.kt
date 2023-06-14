package com.jetpack.kawanusaha.ui.pages.main.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.data.UsahaData
import com.jetpack.kawanusaha.data.UsahaResponse
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.theme.Typography

@Composable
fun UsahaSection(response: UsahaResponse?, navToUsahaDetail: (Int) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .safeDrawingPadding()
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
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = Modifier
        .clickable { navToUsahaDetail(id) }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp),
        ) {
            Text(text = title, style = Typography.body1, maxLines = 1, textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun CallUsahaLists(mainViewModel: MainViewModel) {
    LaunchedEffect(mainViewModel.listUsaha) {
        mainViewModel.getListUsaha()
    }
}