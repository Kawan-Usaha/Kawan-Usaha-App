package com.jetpack.kawanusaha.ui.pages.main.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.data.Category
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.shimmerBrush
import com.jetpack.kawanusaha.ui.theme.Typography


@Composable
fun CategorySection(mainViewModel: MainViewModel) {
    mainViewModel.getCategory()
    val category by mainViewModel.categoryList.collectAsState()
    LazyRow(
        horizontalArrangement = Arrangement.SpaceAround,
        contentPadding = PaddingValues(horizontal = 5.dp)
    ) {
        items(category?.data?.size ?: 0) {
            CategoryItem(mainViewModel, category!!.data[it])
        }
    }
}

@Composable
fun CategoryItem(mainViewModel: MainViewModel, category: Category) {
    val isSelected by mainViewModel.selectedCategory.collectAsState()
    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .width(90.dp)
        .clickable {
            if (isSelected == category.id){
                mainViewModel.selectThisCategory(0)
            } else {
                mainViewModel.selectThisCategory(category.id)
            }
        }) {
        val showShimmer = remember { mutableStateOf(true) }
        AsyncImage(
            model = category.image,
            contentDescription = category.title,
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
            onSuccess = { showShimmer.value = false }
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = category.title, color = MaterialTheme.colors.onPrimary, textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, style = Typography.body2)
    }
}
