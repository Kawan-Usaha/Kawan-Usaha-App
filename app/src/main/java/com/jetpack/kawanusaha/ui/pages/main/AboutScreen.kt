package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.data.ArticlesItem
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.SectionText
import com.jetpack.kawanusaha.ui.pages.TopBar
import com.jetpack.kawanusaha.ui.pages.main.utils.ArticleSection
import com.jetpack.kawanusaha.ui.pages.main.utils.CallUsahaLists
import com.jetpack.kawanusaha.ui.pages.main.utils.UsahaSection
import com.jetpack.kawanusaha.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AboutScreen(
    loginViewModel: LoginViewModel,
    mainViewModel: MainViewModel,
    navToArticle: (Int) -> Unit,
    navToLanding: () -> Unit,
    navToVerify: () -> Unit,
    navToChangeAbout: () -> Unit,
    navToUsahaDetail: (Int) -> Unit,
    navToAddUsaha: () -> Unit
) {
    val articles: LazyPagingItems<ArticlesItem> =
        mainViewModel.getUserArticles().collectAsLazyPagingItems()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetBackgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.safeDrawingPadding(),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .padding(25.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clickable { navToChangeAbout() },
                    contentAlignment = Alignment.CenterStart
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile"
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = "Edit Profile", style = MaterialTheme.typography.h3)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clickable { navToAddUsaha() },
                    contentAlignment = Alignment.CenterStart
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Usaha"
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = "Add Usaha", style = MaterialTheme.typography.h3)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clickable { },
                    contentAlignment = Alignment.CenterStart
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeveloperBoard,
                            contentDescription = "About Developer"
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = "About Developer", style = MaterialTheme.typography.h3)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clickable { },
                    contentAlignment = Alignment.CenterStart
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = "Settings", style = MaterialTheme.typography.h3)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clickable {
                            loginViewModel.logout()
                            navToLanding()
                        },
                    contentAlignment = Alignment.CenterStart
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Logout"
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = "Logout", style = MaterialTheme.typography.h3)
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopBar {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            if (modalBottomSheetState.isVisible) {
                                modalBottomSheetState.hide()
                            } else {
                                modalBottomSheetState.show()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "More"
                        )
                    }
                }
            },
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
                    mainViewModel.getUser()
                    val user by mainViewModel.userProfile.collectAsState(initial = null)
                    if (user != null) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                AsyncImage(
                                    model = "https://www.asiamediajournal.com/wp-content/uploads/2022/11/Default-PFP-1200x1200.jpg",
                                    contentDescription = stringResource(R.string.profile_picture),
                                    placeholder = painterResource(id = R.drawable.profile),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(8.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(10.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = user?.data?.name!!.toString(),
                                        style = MaterialTheme.typography.h2,
                                    )
                                    Text(
                                        text = user?.data?.email!!.toString(),
                                        fontWeight = FontWeight.Normal,
                                        style = MaterialTheme.typography.h3,
                                    )

                                    Text(
                                        text = "Since: " + user?.data?.createdAt!!.slice(
                                            0..9
                                        ),
                                        fontWeight = FontWeight.Normal,
                                        style = MaterialTheme.typography.h3,
                                        softWrap = true
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            if (!(user?.data?.verified!!)) {
                                Button(
                                    onClick = { navToVerify() },
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
                                ) {
                                    Text(
                                        text = stringResource(R.string.verify_account),
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }
                        }
                    }
                }
//                item {
//                    Card(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
//                        CallData(mainViewModel = mainViewModel)
//                        val user by mainViewModel.userProfile.collectAsState(initial = null)
//                        if (user != null) {
//                            Card(
//                                backgroundColor = MaterialTheme.colors.primary,
//                                elevation = 5.dp,
//                                modifier = Modifier.padding(10.dp),
//                                border = BorderStroke(width = 1.dp, MaterialTheme.colors.secondary)
//                            ) {
//                                Column(
//                                    horizontalAlignment = Alignment.CenterHorizontally,
//                                    modifier = Modifier.padding(10.dp)
//                                ) {
//                                    Text(
//                                        text = user?.data?.name!!.toString(),
//                                        style = MaterialTheme.typography.h3
//                                    )
//                                    Column(
//                                        verticalArrangement = Arrangement.spacedBy(5.dp),
//                                        modifier = Modifier.padding(vertical = 20.dp)
//                                    ) {
//                                        user?.data?.let { userData ->
//                                            listOf(
//                                                stringResource(R.string.email) + ": " to userData.email,
//                                                stringResource(R.string.member_since) to userData.createdAt.slice(
//                                                    0..9
//                                                ),
//                                            ).forEach { (label, value) ->
//                                                Row(verticalAlignment = Alignment.CenterVertically) {
//                                                    Text(
//                                                        text = label,
//                                                        fontWeight = FontWeight.SemiBold,
//                                                        style = MaterialTheme.typography.body1,
//                                                        modifier = Modifier.weight(1f)
//                                                    )
//                                                    Text(
//                                                        text = value,
//                                                        style = MaterialTheme.typography.body1,
//                                                        softWrap = true
//                                                    )
//                                                }
//                                            }
//
//                                        }
//                                    }
//                                    Spacer(modifier = Modifier.height(10.dp))
//                                    if (!(user?.data?.verified!!)) {
//                                        Button(
//                                            onClick = { navToVerify() },
//                                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
//                                        ) {
//                                            Text(
//                                                text = stringResource(R.string.verify_account),
//                                                style = MaterialTheme.typography.body1
//                                            )
//                                        }
//                                    }
//
////                                    Button(
////                                        onClick = {
////                                            loginViewModel.logout()
////                                            navToLanding()
////                                        },
////                                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
////                                    ) {
////                                        Text(
////                                            text = stringResource(R.string.sign_out),
////                                            style = MaterialTheme.typography.body1
////                                        )
////                                    }
//                                }
//                            }
//                        }
//                    }
//                }
                item {
                    Divider(
                        color = MaterialTheme.colors.onPrimary, modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp), startIndent = 2.dp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ){
                        SectionText(
                            text = stringResource(R.string.usaha),
                            style = Typography.h3,
                            modifier = Modifier
                        )
                    }
                }
                item {
                    CallUsahaLists(mainViewModel = mainViewModel)
                    val data by mainViewModel.listUsaha.collectAsState(null)
                    UsahaSection(response = data, navToUsahaDetail = navToUsahaDetail)
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    // Recommendation Articles Section
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ){
                        SectionText(
                            text = stringResource(R.string.your_articles),
                            style = MaterialTheme.typography.h3,
                            modifier = Modifier
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    ArticleSection(articles, navToArticle)
                }
            }
        }
    }
}
