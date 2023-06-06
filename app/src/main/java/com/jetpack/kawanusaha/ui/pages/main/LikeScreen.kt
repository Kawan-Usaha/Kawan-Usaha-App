@file:OptIn(ExperimentalMaterialApi::class)

package com.jetpack.kawanusaha.ui.pages.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.db.fav.DbFav
import com.jetpack.kawanusaha.main.LikeViewModel
import com.jetpack.kawanusaha.main.ListArticle
import com.jetpack.kawanusaha.main.ListArticleViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LikeScreen(
    navToDetailList: (String, String?) -> Unit,
    viewModel: LikeViewModel
){
    val coroutineScope = rememberCoroutineScope()
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    val data = listOf<ListArticle>()
    val viewState = viewModel.consumableState().collectAsState()
    val viewEvent = ListArticleViewState()

    val context = LocalContext.current
    val list by viewModel.allData.observeAsState(listOf())
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(32.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = { coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse()}}) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                        }
                        Text(
                            "Create List",
                            style = MaterialTheme.typography.h3
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = {title = it},
                        label = {Text("List name")},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colors.onPrimary,
                            cursorColor = MaterialTheme.colors.onPrimary,
                            errorBorderColor = Color.Red,
                            focusedBorderColor = MaterialTheme.colors.secondary,
                            unfocusedBorderColor = MaterialTheme.colors.surface,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            unfocusedLabelColor = MaterialTheme.colors.surface
                        ),
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = {description = it},
                        modifier = Modifier.fillMaxWidth(),
                        label = {Text("Description")},
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colors.onPrimary,
                            cursorColor = MaterialTheme.colors.onPrimary,
                            errorBorderColor = Color.Red,
                            focusedBorderColor = MaterialTheme.colors.secondary,
                            unfocusedBorderColor = MaterialTheme.colors.surface,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            unfocusedLabelColor = MaterialTheme.colors.surface
                        ),
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {viewModel.add(title.text, description.text)},
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Add article list")
                    }
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        sheetElevation = 10.dp,
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
                    items(list, key = {item -> item.id}){
                        ItemList(viewModel, { navToDetailList(it.listName,it.description) }, dbFav = it)
                    }
                }
            }
        }
    }
}
@Composable
fun TopBarFavorite(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        horizontalArrangement = Arrangement.Start,
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
    viewModel: LikeViewModel,
    navToDetailList: () -> Unit,
    dbFav: DbFav,
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val count = listOf(dbFav)

    Card(
            modifier = Modifier
                .clickable { navToDetailList() }
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
                        text = dbFav.listName,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                    )
                    Box(
                        contentAlignment = Alignment.Center
                    ){
                        IconButton(onClick = {expanded = !expanded}) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More")

                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .background(MaterialTheme.colors.primary)
                        ) {
                            DropdownMenuItem(
                                onClick = { Toast.makeText(context, "Edit List", Toast.LENGTH_SHORT).show() }
                            ) {
                                Text("Edit")
                            }
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.deleteById(dbFav.listName)
                                    Toast.makeText(context, "Delete List", Toast.LENGTH_SHORT).show() }
                            ){
                                Text("Delete List")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = "${count.count()} Article")
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

@Composable
fun DropDownMenu() {

}