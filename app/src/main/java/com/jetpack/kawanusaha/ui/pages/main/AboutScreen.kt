package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.TopBar
import com.jetpack.kawanusaha.ui.pages.shimmerBrush
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
        },
        modifier = Modifier.safeDrawingPadding()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                val showShimmer = remember { mutableStateOf(true) }
                AsyncImage(
                    model = "URL DISINI",
                    contentDescription = "Profile Picture",
                    placeholder = painterResource(id = R.drawable.profile),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = showShimmer.value
                            )
                        ),
                    onSuccess = {showShimmer.value = false}
                )
            }
            item {
                Card(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
                    CallData(mainViewModel = mainViewModel)
                    val user by mainViewModel.userProfile.collectAsState(initial = null)
                    if (user != null) {
                        Card(
                            backgroundColor = MaterialTheme.colors.primary,
                            elevation = 5.dp,
                            modifier = Modifier.padding(10.dp),
                            border = BorderStroke(width = 1.dp, MaterialTheme.colors.secondary)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = user?.data?.name!!.toString(),
                                    style = MaterialTheme.typography.h3
                                )
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    contentPadding = PaddingValues(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),
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
                                                Text(text = label, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.body1)
                                            }
                                            item {
                                                Text(text = value, style = MaterialTheme.typography.body1)
                                            }
                                        }

                                    }
                                }
                                Spacer(modifier = Modifier.height(15.dp))
                                if (!(user?.data?.verified!!)) {
                                    Button(
                                        onClick = { navToVerify() },
                                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
                                    ) {
                                        Text(text = "Verify Account", style = MaterialTheme.typography.body1)
                                    }
                                }

                                Button(
                                    onClick = {
                                        loginViewModel.logout()
                                        navToLanding()
                                    },
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
                                ) {
                                    Text(text = "Sign Out", style = MaterialTheme.typography.body1)
                                }
                            }
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colors.onPrimary, modifier = Modifier.fillMaxWidth(), startIndent = 2.dp)
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