package com.jetpack.kawanusaha.ui.pages.main.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.ui.pages.shimmerBrush


@Composable
fun CategorySection() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(20) {
            CategoryItem()
        }
    }
}

@Composable
fun CategoryItem() {
    //TODO: Change This
    Column {
        val showShimmer = remember { mutableStateOf(true) }
        AsyncImage(
            model = "URL DISINI",
            contentDescription = stringResource(R.string.category),
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
            onSuccess = { showShimmer.value = false }
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Google", color = MaterialTheme.colors.onPrimary)
    }
}
