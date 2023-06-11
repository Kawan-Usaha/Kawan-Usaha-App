package com.jetpack.kawanusaha.ui.pages.main

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.tools.getFileFromUri
import java.io.File

@Composable
fun AddArticleScreen(
    mainViewModel: MainViewModel,
    navigateToMain: () -> Unit,
    navToCamera: () -> Unit
) {
    val articleCache by mainViewModel.articleCache.collectAsState(initial = null)
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var body by remember { mutableStateOf(TextFieldValue("")) }
    var category by remember { mutableStateOf(TextFieldValue("1")) }
    val status by mainViewModel.status.collectAsState(initial = false)
    val image by mainViewModel.imageFile.collectAsState(initial = Uri.parse("file://dev/null"))

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.primary,
                    title = {
                        Text(
                            text = "Create Your Article",
                            style = MaterialTheme.typography.h3
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigateToMain() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Add Article",
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    },
                    actions = {
                        Button(
                            onClick = {
                                mainViewModel.createArticle(
                                    title = title.text,
                                    content = body.text,
                                    category = category.text.toInt()
                                )
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                        ) {
                            Text(text = "Create", style = MaterialTheme.typography.body1)
                        }
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    mainViewModel.saveArticleCache(
                        title.text,
                        body.text,
                        category.text.toInt()
                    )
                    navToCamera()
                }) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "Add Picture"
                    )
                }
            }
        ) { innerPadding ->
            title = TextFieldValue(articleCache?.article?.title ?: "")
            body = TextFieldValue(articleCache?.article?.content ?: "")
            category = TextFieldValue((articleCache?.category ?: 1).toString())
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                item {
                    Card(
                        Modifier.fillMaxSize(),
                        backgroundColor = MaterialTheme.colors.primary
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        ) {
                            val customTextSelection = TextSelectionColors(
                                handleColor = MaterialTheme.colors.secondary,
                                backgroundColor = MaterialTheme.colors.background
                            )

                            // Title
                            CompositionLocalProvider(LocalTextSelectionColors provides customTextSelection) {
                                TextField(
                                    value = title,
                                    onValueChange = { title = it },
                                    textStyle = MaterialTheme.typography.h3,
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = MaterialTheme.colors.primary,
                                        cursorColor = MaterialTheme.colors.onPrimary,
                                        unfocusedIndicatorColor = MaterialTheme.colors.primary,
                                        focusedIndicatorColor = MaterialTheme.colors.primary
                                    ),
                                    singleLine = true,
                                    placeholder = {
                                        Text(
                                            text = "Enter Title Here",
                                            color = MaterialTheme.colors.surface,
                                            style = MaterialTheme.typography.h3
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.height(5.dp))

                                // body
                                TextField(
                                    value = body,
                                    onValueChange = { body = it },
                                    textStyle = MaterialTheme.typography.body1,
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = MaterialTheme.colors.primary,
                                        cursorColor = MaterialTheme.colors.onPrimary,
                                        unfocusedIndicatorColor = MaterialTheme.colors.primary,
                                        focusedIndicatorColor = MaterialTheme.colors.primary
                                    ),
                                    placeholder = {
                                        Text(
                                            text = "Enter body here",
                                            color = MaterialTheme.colors.surface,
                                            style = MaterialTheme.typography.body1
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 16.dp)){
                                if (image != Uri.parse("file://dev/null")) {
                                    val file = getFileFromUri(LocalContext.current, image) as File
                                    AsyncImage(
                                        model = image,
                                        contentDescription = "Image Preview",
                                    )
                                    IconButton(onClick = { mainViewModel.clearImage() },
                                        modifier = Modifier.align(Alignment.TopEnd) )  {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete Picture")
                                    }
                                }
                            }

                        }
                    }
                }
            }
            LaunchedEffect(status) {
                if (status) {
                    navigateToMain()
                    mainViewModel.clearStatus()
                    mainViewModel.clearArticleCache()
                    mainViewModel.clearImage()
                }
            }
        }
    }
}

private fun loadImageBitmap(imageFile: File): ImageBitmap {
    // Load the image file as a Bitmap using platform-specific APIs
    // Here, you can use BitmapFactory.decodeFile or any other appropriate method
    return BitmapFactory.decodeFile(imageFile.absolutePath)?.asImageBitmap()
        ?: throw IllegalStateException("Failed to load bitmap from file: $imageFile")
}

//@Composable
//fun BottomBarAddPage(
//) {
//    var isSelectedBold by remember { mutableStateOf(false) }
//    var isSelectedItalic by rememberSaveable { mutableStateOf(false) }
//    var isSelectedUnderline by remember { mutableStateOf(false) }
//    BottomAppBar(
//        backgroundColor = MaterialTheme.colors.primary,
//        elevation = 5.dp,
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(5.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//            IconButton(onClick = { isSelectedBold = !isSelectedBold}) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_bold_grey),
//                    contentDescription = "Bold",
//                    tint = if(isSelectedBold){
//                        MaterialTheme.colors.onPrimary
//                    } else {
//                        MaterialTheme.colors.surface
//                    }
//                )
//            }
//            IconButton(onClick = { isSelectedItalic = !isSelectedItalic }) {
//                Icon(
//                    painter = painterResource(R.drawable.baseline_format_italic_24),
//                    contentDescription = "Italic",
//                    tint = if(isSelectedItalic){
//                        MaterialTheme.colors.onPrimary
//                    } else {
//                        MaterialTheme.colors.surface
//                    }
//                )
//            }
//            IconButton(onClick = { isSelectedUnderline = !isSelectedUnderline }) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_underline_grey),
//                    contentDescription = "Italic",
//                    tint = if(isSelectedUnderline){
//                        MaterialTheme.colors.onPrimary
//                    } else {
//                        MaterialTheme.colors.surface
//                    }
//                )
//            }
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.End,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                IconButton(onClick = { /*TODO*/ }) {
//                    Icon(
//                        painter = painterResource(R.drawable.baseline_image_24),
//                        contentDescription = "Image",
//                        modifier = Modifier
//                            .weight(1f)
//                    )
//                }
//            }
//
//        }
//    }
//}


data class FontEdit(
    val title: String,
    val icon: Painter,
    val color: Color,
    val isSelected: () -> Unit,
    val fontWeight: FontWeight? = null,
    val fontStyle: FontStyle? = null,
    val spanStyle: SpanStyle? = null,
)


