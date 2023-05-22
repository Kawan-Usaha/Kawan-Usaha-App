package com.jetpack.kawanusaha.ui

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.ui.pages.LandingScreen
import com.jetpack.kawanusaha.ui.pages.LoginScreen
import com.jetpack.kawanusaha.ui.pages.MainScreen
import com.jetpack.kawanusaha.ui.pages.RegisterScreen

@Composable
fun NavigationScreen(viewModel: LoginViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "landing_screen") {

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
            LoginScreen(viewModel = viewModel, {
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
            RegisterScreen(viewModel = viewModel, {
                // RegisterScreen to LoginScreen
                navController.navigate("login_screen")
            }, {
                // RegisterScreen to LandingScreen
                navController.navigate("landing_screen")
            })
        }

        // MainScreen Navigation
        composable(route = "main_screen"){
            MainScreen()
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