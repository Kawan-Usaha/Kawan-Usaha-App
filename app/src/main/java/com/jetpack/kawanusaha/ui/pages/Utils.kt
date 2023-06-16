package com.jetpack.kawanusaha.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jetpack.kawanusaha.R


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
                        title = stringResource(R.string.Feeds), //change into string resource
                        icon = Icons.Default.List,
                        screen = Screen.Feeds
                    ),
                    NavigationItem(
                        title = stringResource(R.string.Explore),
                        icon = Icons.Default.Search,
                        screen = Screen.Explore
                    ),
                    NavigationItem(
                        title = stringResource(R.string.chat),
                        icon = Icons.Default.Info,
                        screen = Screen.Chat
                    ),
                    NavigationItem(
                        title = stringResource(R.string.favorite),
                        icon = Icons.Default.Favorite,
                        screen = Screen.Like
                    ),
                    NavigationItem(
                        title = stringResource(R.string.profile),
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

@Composable
fun SectionText(text: String, style: TextStyle, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = MaterialTheme.colors.onPrimary,
        style = style,
        modifier = modifier
    )
}

suspend fun scrollToItem(state: LazyListState, item: Int) {
    state.animateScrollToItem(item)
}

suspend fun scrollToBottom(state: LazyListState) {
    state.animateScrollToItem(state.layoutInfo.totalItemsCount)
}