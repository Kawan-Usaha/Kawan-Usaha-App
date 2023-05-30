package com.jetpack.kawanusaha.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

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
                        title = "Feeds", //ubah jadi string resource
                        icon = Icons.Default.List,
                        screen = Screen.Feeds
                    ),
                    NavigationItem(
                        title = "Usaha",
                        icon = Icons.Default.Create,
                        screen = Screen.Usaha
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
                                        saveState = true
                                    }
                                    restoreState = true
                                    launchSingleTop = true
                                }
                            }
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
    object Usaha : Screen("usaha_screen")
    object Chat : Screen("chat_screen")
    object Like : Screen("like_screen")
    object Profile : Screen("about_screen")
}