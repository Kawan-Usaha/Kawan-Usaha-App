package com.jetpack.kawanusaha.ui.pages.main

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.jetpack.kawanusaha.R
import com.jetpack.kawanusaha.data.CreateArticleRequest
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.BackPressHandler
import java.io.File

@Composable
fun AddArticleScreen(
    mainViewModel: MainViewModel,
    navigateToMain: () -> Unit,
    navToCamera: () -> Unit,
    navBack: () -> Unit
) {
    val articleCache by mainViewModel.articleCache.collectAsState()
    var title by remember { mutableStateOf(TextFieldValue(articleCache?.article?.title ?: "")) }
    var body by remember { mutableStateOf(TextFieldValue(articleCache?.article?.content ?: "")) }
    var category by remember { mutableStateOf(articleCache?.category ?: 0) }
    val status by mainViewModel.status.collectAsState(initial = false)
    val image by mainViewModel.imageFile.collectAsState(initial = Uri.parse("file://dev/null"))
    val context = LocalContext.current


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
                            text = stringResource(R.string.create_your_article),
                            style = MaterialTheme.typography.h3
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            mainViewModel.saveArticleCache(
                                title.text,
                                body.text,
                                category
                            )
                            navigateToMain() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.close_add_article),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    },
                    actions = {
                        Button(
                            onClick = {
                                if (category != 0){
                                    mainViewModel.createArticle(
                                        title = title.text,
                                        content = body.text,
                                        category = category
                                    )
                                } else {
                                    Toast.makeText(context, R.string.please_select_category, Toast.LENGTH_SHORT).show()
                                }
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                        ) {
                            Text(
                                text = stringResource(R.string.create),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    mainViewModel.saveArticleCache(
                        title.text,
                        body.text,
                        category
                    )
                    navToCamera()
                }) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = stringResource(R.string.add_picture)
                    )
                }
            }
        ) { innerPadding ->
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
                                .padding(start = 16.dp, end = 16.dp)
                        ) {
                            var isClicked by remember { mutableStateOf(false) }
                            var newCategory by remember { mutableStateOf("") }
                            val icon = if (isClicked) {
                                Icons.Default.Close
                            } else {
                                Icons.Default.Add
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.category),
                                    style = MaterialTheme.typography.h3
                                )
                                Spacer(modifier = Modifier.width(16.dp))
//                                if(isClicked){
//                                    OutlinedTextField(
//                                        value = newCategory,
//                                        onValueChange = {newCategory = it},
//                                        label = {Text(text = "Add New Category")},
//                                        trailingIcon = {
//                                            Card(
//                                                backgroundColor = MaterialTheme.colors.secondary,
//                                                shape = RoundedCornerShape(20.dp),
//                                                modifier = Modifier.clickable { }
//                                            ) {
//                                                Row(
//                                                    verticalAlignment = Alignment.CenterVertically,
//                                                    horizontalArrangement = Arrangement.Center
//                                                ) {
//                                                    Text("Add")
//                                                }
//                                            }
//                                        },
//                                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                                            unfocusedBorderColor = MaterialTheme.colors.secondary,
//                                            focusedBorderColor = MaterialTheme.colors.secondary,
//                                            unfocusedLabelColor = MaterialTheme.colors.surface,
//                                            focusedLabelColor = MaterialTheme.colors.secondary,
//                                            cursorColor = MaterialTheme.colors.onPrimary
//                                        )
//                                    )
//                                } else {
                                category = DropDownMenu(mainViewModel, category)
//                                }
//                                IconButton(onClick = { isClicked = !isClicked }) {
//                                    Icon(imageVector = icon,
//                                        contentDescription = "Add New Category",
//                                        tint = MaterialTheme.colors.primary,
//                                        modifier = Modifier
//                                            .background(MaterialTheme.colors.secondary, shape = CircleShape)
//                                    )
//                                }
                            }
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
                                            text = stringResource(R.string.enter_title_here),
                                            color = MaterialTheme.colors.surface,
                                            style = MaterialTheme.typography.h3
                                        )
                                    },
                                )
                                Spacer(modifier = Modifier.height(5.dp))

                                // body
                                TextField(
                                    value = body,
                                    onValueChange = { body = it },
                                    textStyle = MaterialTheme.typography.body2,
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = MaterialTheme.colors.primary,
                                        cursorColor = MaterialTheme.colors.onPrimary,
                                        unfocusedIndicatorColor = MaterialTheme.colors.primary,
                                        focusedIndicatorColor = MaterialTheme.colors.primary
                                    ),
                                    placeholder = {
                                        Text(
                                            text = stringResource(R.string.enter_body_here),
                                            color = MaterialTheme.colors.surface,
                                            style = MaterialTheme.typography.body2
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 16.dp)
                            ) {
                                if (image != Uri.parse("file://dev/null")) {
                                    AsyncImage(
                                        model = image,
                                        contentDescription = stringResource(R.string.image_preview),
                                    )
                                    IconButton(
                                        onClick = { mainViewModel.clearImage() },
                                        modifier = Modifier.align(Alignment.TopEnd)
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = stringResource(R.string.delete_picture)
                                        )
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

        BackPressHandler(
            onBackPressed = {
                navBack()
                mainViewModel.saveArticleCache(
                    title.text,
                    body.text,
                    category
                )
            }
        )
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

@Composable
fun DropDownMenu(
    mainViewModel: MainViewModel,
    category: Int
) : Int {
    var expanded by remember { mutableStateOf(false) }
    val list by mainViewModel.categoryList.collectAsState()
    var selectedIndex by remember { mutableStateOf(category) }
    var initItem = ""
    list?.data?.forEach {
        if (it.id == selectedIndex){
            initItem = it.title
        }
    }
    var selectedItem by remember { mutableStateOf(initItem) }
    var textFilledSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(
        modifier = Modifier
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .onGloballyPositioned { coordinates ->
                    textFilledSize = coordinates.size.toSize()
                },
            label = { Text(text = stringResource(R.string.select_category)) },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            readOnly = true,
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                expanded = !expanded
                            }
                        }
                    }
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedLabelColor = MaterialTheme.colors.surface,
                focusedLabelColor = MaterialTheme.colors.secondary
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFilledSize.width.toDp() })
                .background(MaterialTheme.colors.primary)
        ) {
            list?.data?.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedItem = label.title
                    selectedIndex = label.id
                    expanded = false
                }) {
                    Text(text = label.title)
                }
            }
        }
    }
    return selectedIndex
}


data class FontEdit(
    val title: String,
    val icon: Painter,
    val color: Color,
    val isSelected: () -> Unit,
    val fontWeight: FontWeight? = null,
    val fontStyle: FontStyle? = null,
    val spanStyle: SpanStyle? = null,
)


