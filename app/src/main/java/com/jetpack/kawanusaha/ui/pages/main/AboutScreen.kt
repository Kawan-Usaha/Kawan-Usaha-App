package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.background
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
import com.jetpack.kawanusaha.ui.pages.shimmerBrush
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
    navToAddUsaha: () -> Unit,
    navToAboutDeveloper: () -> Unit,
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
                            contentDescription = stringResource(R.string.edit_profile)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = stringResource(R.string.edit_profile), style = MaterialTheme.typography.h3)
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
                            contentDescription = stringResource(R.string.add_usaha)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = stringResource(R.string.add_usaha), style = MaterialTheme.typography.h3)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clickable { navToAboutDeveloper() },
                    contentAlignment = Alignment.CenterStart
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeveloperBoard,
                            contentDescription = stringResource(R.string.about_developer)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = stringResource(R.string.about_developer), style = MaterialTheme.typography.h3)
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
                            contentDescription = stringResource(R.string.settings)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = stringResource(R.string.settings), style = MaterialTheme.typography.h3)
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
                            contentDescription = stringResource(R.string.sign_out)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(text = stringResource(R.string.sign_out), style = MaterialTheme.typography.h3)
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
                            contentDescription = stringResource(R.string.More),
                        )
                    }
                }
            },
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    mainViewModel.getUser()
                    val user by mainViewModel.userProfile.collectAsState(initial = null)
                    Card(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
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
                                    val showShimmer = remember { mutableStateOf(true) }
                                    AsyncImage(
                                        model = user?.data?.image ?: "",
                                        contentDescription = stringResource(R.string.profile_picture),
                                        placeholder = painterResource(id = R.drawable.profile),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(120.dp)
                                            .padding(8.dp)
                                            .clip(CircleShape)
                                            .background(
                                                shimmerBrush(
                                                    targetValue = 1300f,
                                                    showShimmer = showShimmer.value
                                                )
                                            ),
                                        onSuccess = { showShimmer.value = false },
                                        onError = {
                                            showShimmer.value = true
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(10.dp),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Row (verticalAlignment = Alignment.CenterVertically){
                                            Text(
                                                text = user?.data?.name ?: "",
                                                style = MaterialTheme.typography.h2,
                                            )
                                            Spacer(modifier = Modifier.width(5.dp))
                                            if ((user?.data?.verified!!)) {
                                                Icon(
                                                    imageVector = Icons.Default.Verified,
                                                    contentDescription = "Verified",
                                                    tint = MaterialTheme.colors.secondary
                                                )
                                            }
                                        }
                                        Text(
                                            text = user?.data?.email ?: "",
                                            fontWeight = FontWeight.Normal,
                                            style = MaterialTheme.typography.h3,
                                        )

                                        Text(
                                            text = stringResource(R.string.since) + ": " + user?.data?.createdAt?.slice(
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
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxWidth()
                                    ){
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
                    }
                    Divider(
                        color = MaterialTheme.colors.onPrimary, modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp), startIndent = 2.dp
                    )
                }
                item {
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
