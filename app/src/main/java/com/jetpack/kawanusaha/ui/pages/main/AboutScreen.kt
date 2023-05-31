package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.jetpack.kawanusaha.ui.pages.TopBar
import com.jetpack.kawanusaha.ui.theme.Typography

@Composable
fun AboutScreen(
    loginViewModel: LoginViewModel,
    mainViewModel: MainViewModel,
    navBack: () -> Unit,
    navToLanding: () -> Unit,
    navToVerify: () -> Unit,
    navToChangeAbout: () -> Unit,
    navToUsahaDetail: (Int) -> Unit,
    navToAddUsaha: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar {
                IconButton(onClick = {
                    navToChangeAbout()
                }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Edit"
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AsyncImage(
                    model = "https://www.asiamediajournal.com/wp-content/uploads/2022/11/Default-PFP-1200x1200.jpg",
                    contentDescription = "Profile Picture",
                    placeholder = painterResource(id = R.drawable.profile),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .clip(CircleShape)
                )
            }
            item {
                Card(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
                    CallData(mainViewModel = mainViewModel)
                    val user by mainViewModel.userProfile.collectAsState(initial = null)
                    if (user != null) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = user?.data?.name!!.toString())
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                modifier = Modifier.height(120.dp)
                            ) {
                                user?.data?.let { userData ->
                                    listOf(
                                        "Account Id: " to userData.userId,
                                        "Email: " to userData.email,
                                        "Member Since: " to userData.createdAt.slice(0..9),
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
                            if (!(user?.data?.verified!!)) {
                                Button(onClick = {
                                    navToVerify()
                                }) {
                                    Text(text = "Verify Account")
                                }
                            }
                        }
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        loginViewModel.logout()
                        navToLanding()
                    }) {
                    Text(text = "Sign Out")
                }
            }
            item {
                Row (Modifier.fillMaxWidth()){
                    SectionText(text = "Usaha", style = Typography.h3 , modifier = Modifier.weight(1f))
                    SectionText(text = "+", style = Typography.h3 , modifier = Modifier.clickable { navToAddUsaha() })
                }
            }
            item {
                CallUsahaLists(mainViewModel = mainViewModel)
                val data by mainViewModel.listUsaha.collectAsState(null)
                UsahaSection(response = data, navToUsahaDetail = navToUsahaDetail)
            }
        }
    }
}

@Composable
private fun CallData(mainViewModel: MainViewModel) {
    LaunchedEffect(mainViewModel) {
        mainViewModel.getUser()
    }
}