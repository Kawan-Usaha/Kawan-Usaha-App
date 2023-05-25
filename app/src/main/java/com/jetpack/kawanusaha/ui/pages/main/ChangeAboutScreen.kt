package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.authentication.mToast

@Composable
fun ChangeAboutScreen(mainViewModel: MainViewModel, navBack: () -> Unit) {
    val mContext = LocalContext.current
    val newName by remember { mutableStateOf(TextFieldValue("")) }
    val newEmail by remember { mutableStateOf(TextFieldValue("")) }
    var isChanged : Boolean = remember { false }
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
            Box{
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
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile Picture",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clickable {
                            // TODO open camera / gallery
                        }
                )
            }
            Card(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
                val user by mainViewModel.userProfile.collectAsState(initial = null)
                if (user != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = user?.data?.name!!.toString())
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                            user?.data?.let { userData ->
                                item { Text(text = "Account Id: ") }
                                item { TextField(
                                    value = userData.userId,
                                    onValueChange = {},
                                    enabled = false
                                ) }
                                item { Text(text = "Name: ") }
                                item { TextField(
                                    value = userData.name,
                                    onValueChange = { isChanged = true },
                                ) }
                                item { Text(text = "Email: ")}
                                item { TextField(
                                    value = userData.email,
                                    onValueChange = { isChanged = true },
                                ) }
                            }
                        }
                        Button(
                            onClick = {
//                                mainViewModel.saveProfileChange(newName.text, newEmail.text)
                                      },
                            enabled = isChanged
                        ) {
                            Text(text = "Save Change")
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(mainViewModel.status){
        if (mainViewModel.status.value){
            mToast(mContext, "Changes Saved")
            navBack()
        } else {
            mToast(mContext, "Failed to Save Changes")
        }
    }
}