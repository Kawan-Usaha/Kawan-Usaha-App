package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.kawanusaha.R

@Composable
fun AboutDeveloperScreen(
    navBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(stringResource(R.string.about_developer))},
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ){
            item{
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .safeDrawingPadding(),
                ) {
                    Text(
                        text = "Project Capstone Bangkit 2023",
                        style = MaterialTheme.typography.h1,
                    )

                    Text(
                        text = "Team ID C23-PS351",
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.h1
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    Row{
                        Profile(
                            image = painterResource(R.drawable.profile),
                            name = "Gabrielle Evan Farrel",
                            id = "(CC) C151DSX2011",
                            modifier = Modifier.weight(1f)
                        )
                        Profile(
                            image = painterResource(R.drawable.profile),
                            name = "Windiarta",
                            id = "(MD) A181DSX3462",
                            modifier = Modifier.weight(1f),
                        )
                        Profile(
                            image = painterResource(R.drawable.profile),
                            name = "Muhammad Naufal Faza",
                            id = "(ML) M181DSX1496",
                            modifier = Modifier.weight(1f),
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row{
                        Profile(
                            image = painterResource(R.drawable.profile),
                            name = "Muhammad Kholif Alami",
                            id = "(CC) C151DSX1941",
                            modifier = Modifier.weight(1f),
                        )
                        Profile(
                            image = painterResource(R.drawable.profile),
                            name = "Frodewin Grimbert",
                            id = "(MD) A181DSX3252",
                            modifier = Modifier.weight(1f),
                        )
                        Profile(
                            image = painterResource(R.drawable.profile),
                            name = "Muhammad Rizki Fadhilla Lubis",
                            id = "(ML) M017DSX3730",
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Profile(
    image: Painter,
    name: String,
    id: String,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = image,
            contentDescription = stringResource(R.string.profile),
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        Text(
            text = id,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center

        )
    }
}