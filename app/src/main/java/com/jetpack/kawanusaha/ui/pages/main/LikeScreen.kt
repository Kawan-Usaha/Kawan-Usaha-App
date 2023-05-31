package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R

@Composable
fun LikeScreen(

){
    Scaffold(topBar = { TopBarFavorite() }, modifier = Modifier.padding(15.dp) ){innerPadding ->
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

@Composable
fun TopBarFavorite() {
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
            onClick = {},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
        ) {
            Text(text = "Add article list")
        }
    }
}

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
                    text = "Article List Name",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = { /*TODO*/ }) {
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