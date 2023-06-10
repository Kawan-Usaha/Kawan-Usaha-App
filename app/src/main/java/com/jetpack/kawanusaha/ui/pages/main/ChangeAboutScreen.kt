package com.jetpack.kawanusaha.ui.pages.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.authentication.mToast

@Composable
fun ChangeAboutScreen(mainViewModel: MainViewModel, navBack: () -> Unit) {
    var newName by remember { mutableStateOf(TextFieldValue("")) }
    var newEmail by remember { mutableStateOf(TextFieldValue("")) }
    val status by mainViewModel.status.collectAsState(initial = false)
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
        },
        modifier = Modifier.safeDrawingPadding()
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
                        .size(200.dp)
                        .padding(8.dp)
                        .clip(CircleShape)
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    tint = Color.White,
                    contentDescription = "Edit Profile Picture",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                        .alpha(0.8f)
                        .clickable {
                            // TODO open camera / gallery
                        }
                )
            }
            Card(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 5.dp,
                modifier = Modifier.padding(10.dp),
                border = BorderStroke(width = 1.dp, MaterialTheme.colors.secondary)
            ){
                val user by mainViewModel.userProfile.collectAsState(initial = null)
                if (user != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(10.dp)) {
                        Text(text = user?.data?.name!!.toString(), style = MaterialTheme.typography.h3)
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                                user?.data?.let { userData ->
                                newName = TextFieldValue(userData.name)
                                newEmail = TextFieldValue(userData.email)
                                item { Text(text = "Account Id: ",fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.body1) }
                                item { TextField(
                                    value = userData.userId,
                                    onValueChange = {  },
                                    readOnly = true
                                ) }
                                item { Text(text = "Name: ",fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.body1) }
                                item { TextField(
                                    value = newName,
                                    onValueChange = {
                                        newName = it},
                                ) }
                                item { Text(text = "Email: ",fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.body1)}
                                item { TextField(
                                    value = newEmail,
                                    onValueChange = {
                                        newEmail = it },
                                ) }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        user?.data?.let { userData ->
                            Button(
                                onClick = {
                                    mainViewModel.saveProfileChange(newName.text, newEmail.text)
                                },
                                //TODO pake regex
                                enabled = (newName.text != userData.name || newEmail.text != userData.email) && (newName.text != "" || newEmail.text != ""),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.secondary,
                                    disabledBackgroundColor = MaterialTheme.colors.surface,
                                )
                            ) {
                                Text(text = "Save Change")
                                LaunchedEffect(status){
                                    if (status){
                                        mainViewModel.clearStatus()
                                        navBack()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(
                Modifier.windowInsetsBottomHeight(
                    WindowInsets.systemBars
                )
            )
        }
    }
}