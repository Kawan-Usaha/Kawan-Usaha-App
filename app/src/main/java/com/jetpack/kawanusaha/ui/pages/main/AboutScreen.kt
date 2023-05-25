package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.main.MainViewModel

@Composable
fun AboutScreen(
    loginViewModel: LoginViewModel,
    mainViewModel: MainViewModel,
    navBack: () -> Unit,
    navToLanding: () -> Unit,
    navToVerify: () -> Unit,
    navToChangeAbout: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(text = "Profile")
                },
                actions = {
                    IconButton(onClick = {
                        navToChangeAbout()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Edit"
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://www.asiamediajournal.com/wp-content/uploads/2022/11/Default-PFP-1200x1200.jpg",
                contentDescription = "Profile Picture",
                placeholder = painterResource(id = R.drawable.profile),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(500.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
            )
            Card(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
                callData(mainViewModel = mainViewModel)
                val user by mainViewModel.userProfile.collectAsState(initial = null)
                if (user != null){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = user?.data?.name!!.toString())
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                            user?.data?.let { userData ->
                                listOf(
                                    "Account Id: " to userData.userId,
                                    "Email: " to userData.email,
                                    "Member Since: " to userData.createdAt.slice(0..10),
                                ).forEach { (label, value) ->
                                    item {
                                        Text(text = label)
                                    }
                                    item {
                                        Text(text = value)
                                    }
                                }

                            }
                        }
                        if (!(user?.data?.verified!!)){
                            Button(onClick = {
                                navToVerify()
                            }) {
                                Text(text = "Verify Account")
                            }
                        }
                    }
                }
            }
            Button(
                onClick = {
                    loginViewModel.logout()
                    navToLanding()
                }) {
                Text(text = "Sign Out")
            }
        }
    }
}

@Composable
fun callData(mainViewModel: MainViewModel){
    LaunchedEffect(mainViewModel){
        mainViewModel.getUser()
    }
}