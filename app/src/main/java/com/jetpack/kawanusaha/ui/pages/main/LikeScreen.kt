@file:OptIn(ExperimentalMaterialApi::class)

package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LikeScreen(
){
    val coroutineScope = rememberCoroutineScope()
    var inputValue by rememberSaveable { mutableStateOf("") }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        IconButton(onClick = { coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse()}}) {
                            Icon(imageVector =
                            Icons.Default.Close, contentDescription = "close"
                            )
                        }
                        Text(
                            "Create List",
                            style = MaterialTheme.typography.h3
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Name List",
                        style = MaterialTheme.typography.body1
                    )
                    TextField(
                        value = inputValue,
                        onValueChange = {inputValue = it}
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = MaterialTheme.colors.primary
    ){
        Scaffold(topBar = { TopBarFavorite(bottomSheetScaffoldState) }, modifier = Modifier.padding(15.dp)) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize(),
                ){
                    item{
                        ItemList{

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarFavorite(
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Your Favorite Page",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                } },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
        ) {
            Text(text = "Add article list")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemList(
    navToDetail: () -> Unit,
) {
        Card(
            modifier = Modifier
                .clickable { navToDetail() }
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.primary,
            elevation = 10.dp,
            shape = RoundedCornerShape(5.dp)
        ){
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Image Account"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Username", style = MaterialTheme.typography.body1)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Create Text",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit List")

                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = "%s Article")
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(painter = painterResource(id = R.drawable.background),
                        contentDescription = "Example image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(width = 150.dp, height = 100.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Image(painter = painterResource(id = R.drawable.background),
                        contentDescription = "Example image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(width = 150.dp, height = 100.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Image(painter = painterResource(id = R.drawable.background),
                        contentDescription = "Example image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(width = 150.dp, height = 100.dp)
                    )
                }
            }
        }
}