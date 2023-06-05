package com.jetpack.kawanusaha.ui.pages.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.LikeViewModel

@Composable
fun ListLikeScreen(
    navBack: () -> Unit,
    viewModel: LikeViewModel,
    name: String,
    description: String?,
) {

    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            title = {Text(
                text = "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )},
            actions = {
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
                        onClick = { Toast.makeText(context, "Delete Article", Toast.LENGTH_SHORT).show() }
                    ){
                        Text("Delete Article")
                    }
                }
            }
        )
    }) {innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            item{
                Header(name, description)
            }
            item {
                ArticleList {

                }
            }
        }
        
    }
}

@Composable
fun Header(
    name: String,
    description: String?,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        backgroundColor = MaterialTheme.colors.primary,
    ){
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .padding(20.dp)
        ){
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
            ) {
                Image(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Image Account",
                    modifier = Modifier
                        .size(50.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Username", style = MaterialTheme.typography.h3)
                    Text(text = "%s Article", style = MaterialTheme.typography.body1)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.h2,

            )
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}
@Composable
fun ArticleList(
    navToDetail: () -> Unit,
) {
    Card(
        modifier = Modifier
            .clickable { navToDetail() }
            .fillMaxWidth()
            .height(220.dp)
            .padding(20.dp),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 10.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.background
                ),
                contentScale = ContentScale.Fit,
                contentDescription = "Image",
                modifier = Modifier.size(130.dp,80.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Image Account"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Username",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Light
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.h3
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Description coba dibuat agak panjang buat nyobain elipses berhasil atau nggak",
                    style = MaterialTheme.typography.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "a few minutes ago",
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete")
            }
        }
    }
}