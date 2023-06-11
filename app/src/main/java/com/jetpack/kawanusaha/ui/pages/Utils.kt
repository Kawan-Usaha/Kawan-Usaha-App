package com.jetpack.kawanusaha.ui.pages

import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jetpack.kawanusaha.main.CameraViewModel
import com.jetpack.kawanusaha.ui.pages.main.getCameraProvider
import java.io.File
import java.util.concurrent.Executor

@Composable
fun LoadingScreen(isLoading: Boolean) {
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun NavFabButton(navigation: () -> Unit) {
    FloatingActionButton(onClick = { navigation() }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
    }
}

@Composable
fun TopBar(action: @Composable () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(color = MaterialTheme.colors.secondaryVariant),
                    ) {
                        append("KAWAN")
                    }
                    withStyle(
                        style = SpanStyle(color = MaterialTheme.colors.secondary)
                    ) {
                        append(" USAHA")
                    }
                },
                style = MaterialTheme.typography.h1
            )
        },
        actions = { action() },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    )
}

@Composable
fun BottomBar(
    navController: NavController, bottomBarState: MutableState<Boolean>
) {
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(
                modifier = Modifier
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val navigationItems = listOf(
                    NavigationItem(
                        title = "Feeds", //change into string resource
                        icon = Icons.Default.List,
                        screen = Screen.Feeds
                    ),
                    NavigationItem(
                        title = "Explore",
                        icon = Icons.Default.Search,
                        screen = Screen.Explore
                    ),
                    NavigationItem(
                        title = "Chat",
                        icon = Icons.Default.Info,
                        screen = Screen.Chat
                    ),
                    NavigationItem(
                        title = "Favorite",
                        icon = Icons.Default.Favorite,
                        screen = Screen.Like
                    ),
                    NavigationItem(
                        title = "Profile",
                        icon = Icons.Default.AccountCircle,
                        screen = Screen.Profile
                    ),
                )
                BottomNavigation {
                    navigationItems.map { item ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) },
                            selected = currentRoute == item.screen.route,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = false
                                    }
                                    restoreState = true
                                    launchSingleTop = true
                                }
                            },
                            selectedContentColor = MaterialTheme.colors.secondary,
                            unselectedContentColor = MaterialTheme.colors.surface
                        )
                    }
                }
            }
        }
    )

}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)

sealed class Screen(val route: String) {
    object Feeds : Screen("main_screen")
    object Explore : Screen("explore_screen")
    object Chat : Screen("chat_screen")
    object Like : Screen("like_screen")
    object Profile : Screen("about_screen")
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition()
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            )
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

//@Composable
//fun CameraView(
//    outputDirectory: File,
//    executor: Executor,
//    onImageCaptured: (Uri) -> Unit,
//    onError: (ImageCaptureException) -> Unit,
//    viewModel: CameraViewModel,
//) {
//    val lensFacing = CameraSelector.LENS_FACING_BACK
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    val preview = Preview.Builder().build()
//    val previewView = remember {PreviewView(context)}
//    val imageCapture: ImageCapture = remember {ImageCapture.Builder().build()}
//
//    val cameraSelector = CameraSelector.Builder()
//        .requireLensFacing(lensFacing)
//        .build()
//
//    LaunchedEffect(lensFacing){
//        val cameraProvider = context.getCameraProvider()
//        cameraProvider.unbindAll()
//        cameraProvider.bindToLifecycle(
//            lifecycleOwner,
//            cameraSelector,
//            preview,
//            imageCapture
//        )
//        preview.setSurfaceProvider(previewView.surfaceProvider)
//    }
//
//    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()){
//        AndroidView({previewView}, modifier = Modifier.fillMaxSize())
//        IconButton(
//            onClick = {
//                      Log.i("Camera", "ON CLICK")
//                viewModel.takePhoto(
//                    filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
//                    imageCapture = imageCapture,
//                    outputDirectory = outputDirectory,
//                    executor, onImageCaptured, onError
//                )
//                      },
//            modifier = Modifier.padding(20.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Sharp.Lens,
//                contentDescription = "Take Picture",
//                tint = MaterialTheme.colors.secondary,
//                modifier = Modifier
//                    .size(100.dp)
//                    .padding(1.dp)
//                    .border(1.dp, Color.White, CircleShape)
//            )
//        }
//    }
//}