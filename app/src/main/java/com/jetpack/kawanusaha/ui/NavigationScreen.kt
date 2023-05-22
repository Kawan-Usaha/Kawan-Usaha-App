package com.jetpack.kawanusaha.ui

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.ui.pages.*
import com.jetpack.kawanusaha.ui.pages.main.AboutScreen
import com.jetpack.kawanusaha.ui.pages.main.ArticleScreen

@Composable
fun NavigationScreen(loginViewModel: LoginViewModel) {
    val navController = rememberNavController()

    // For Development Purpose
    NavHost(navController = navController, startDestination = "main_screen")

    // TODO: Change this
    // For Real Case
//    NavHost(navController = navController, startDestination = "landing_screen")

    {
        // LandingScreen Navigation
        composable(route = "landing_screen") {
            LandingScreen({
                // LandingScreen to LoginScreen
                navController.navigate("login_screen")
            }, {
                // LandingScreen to RegisterScreen
                navController.navigate("register_screen")
            }
            )
        }

        // LoginScreen Navigation
        composable(route = "login_screen") {
            LoginScreen(viewModel = loginViewModel, {
                // LoginScreen to RegisterScreen
                navController.navigate("register_screen")
            }, {
                // LoginScreen to LandingScreen
                navController.navigate("landing_screen")
            }, {
                // LoginScreen to MainScreen
                navController.navigate("main_screen")
            })
        }

        // RegisterScreen Navigation
        composable(route = "register_screen") {
            RegisterScreen(viewModel = loginViewModel, {
                // RegisterScreen to LoginScreen
                navController.navigate("login_screen")
            }, {
                // RegisterScreen to LandingScreen
                navController.navigate("landing_screen")
            })
        }

        // MainScreen Navigation
        composable(route = "main_screen"){
            MainScreen ({
                // MainScreen to ChatScreen
                navController.navigate("chat_screen")
            }, { title ->
                // MainScreen to ArticleScreen
                navController.navigate("article_screen/$title")
            }, {
                navController.navigate("about_screen")
            })
        }

        composable(route = "chat_screen"){
            ChatScreen()
        }

        composable(
            route = "article_screen/{title}",
            arguments = listOf(navArgument("title"){type = NavType.StringType})
        ){
            // Get Title From Argument
            it.arguments?.getString("title")?.let { title ->
                ArticleScreen (articleTitle = title){
                    // Back to Previous Stack
                    navController.navigateUp()
                }
            }
        }

        composable(route = "about_screen"){
            AboutScreen ({
                navController.navigateUp()
            }, {
                navController.navigate("landing_screen")
            })
        }
    }
}

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}