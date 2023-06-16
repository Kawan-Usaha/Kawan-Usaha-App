package com.jetpack.kawanusaha.ui.pages.main.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.data.UsahaData
import com.jetpack.kawanusaha.data.UsahaResponse
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.theme.Typography

@Composable
fun UsahaSection(response: UsahaResponse?, navToUsahaDetail: (Int) -> Unit, isEdit: Boolean, mainViewModel: MainViewModel) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .safeDrawingPadding()
    ) {
        items(response?.data?.size ?: 0) {
            UsahaItem(usaha_name = response?.data?.get(it)!!, navToUsahaDetail, isEdit, mainViewModel)
        }
    }
}

@Composable
fun UsahaItem(usaha_name: UsahaData, navToUsahaDetail: (Int) -> Unit, isEdit: Boolean, mainViewModel: MainViewModel) {
    val title = usaha_name.usaha_name
    val id = usaha_name.id
    val deleted = remember { mutableStateOf(false) }

    if(!deleted.value) {
        Card(
            shape = RoundedCornerShape(20.dp),
            backgroundColor = MaterialTheme.colors.secondary,
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = title,
                    style = Typography.body1,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
                if (isEdit) {
                    IconButton(
                        onClick = { mainViewModel.deleteUsaha(id = id)
                                  deleted.value = true},
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete, contentDescription = "",
                            modifier = Modifier
                                .background(MaterialTheme.colors.primary, CircleShape)

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CallUsahaLists(mainViewModel: MainViewModel) {
    LaunchedEffect(mainViewModel.listUsaha) {
        mainViewModel.getListUsaha()
    }
}