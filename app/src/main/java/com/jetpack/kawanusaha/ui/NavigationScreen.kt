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
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.*
import com.jetpack.kawanusaha.ui.pages.authentication.ForgotPasswordScreen
import com.jetpack.kawanusaha.ui.pages.authentication.RegisterScreen
import com.jetpack.kawanusaha.ui.pages.authentication.VerificationScreen
import com.jetpack.kawanusaha.ui.pages.main.AboutScreen
import com.jetpack.kawanusaha.ui.pages.main.ArticleScreen
import com.jetpack.kawanusaha.ui.pages.main.MainScreen

// TODO Security Leak in passing password
@Composable
fun NavigationScreen(loginViewModel: LoginViewModel, mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    val startDestination: String = if(loginViewModel.isLoggedIn()) "main_screen" else "landing_screen"

    NavHost(navController = navController, startDestination = startDestination)
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
            }, {
                // LoginScreen to ForgotPasswordScreen
                navController.navigate("forgot_password_screen")
            })
        }

        // RegisterScreen Navigation
        composable(route = "register_screen") {
            RegisterScreen(viewModel = loginViewModel, {
                // RegisterScreen to VerificationScreen
                navController.navigate("main_screen")
            }, {
                // RegisterScreen to LandingScreen
                navController.navigate("landing_screen")
            }, {
                navController.navigate("login_screen")
            })
        }

        // VerificationScreen Navigation
        composable(
            route = "verification_screen/{email}/{password}/{passwordConfirm}",
            arguments = listOf(
                navArgument("email"){type = NavType.StringType},
                navArgument("password"){type = NavType.StringType},
                navArgument("passwordConfirm"){type = NavType.StringType},
            ),
        ){
            VerificationScreen(
                viewModel = loginViewModel,
                email = it.arguments?.getString("email"),
                password = it.arguments?.getString("password"),
                passwordConfirm = it.arguments?.getString("passwordConfirm")
            ) {
                navController.navigate("main_screen")
            }
        }

        // VerificationScreen Navigation
        composable(route = "verification_screen"){
            VerificationScreen(
                viewModel = loginViewModel,
                email = null,
                password = null,
                passwordConfirm = null
            ) {
                navController.navigate("main_screen")
            }
        }

        // ForgotPassword Navigation
        composable(route = "forgot_password_screen"){
            ForgotPasswordScreen(viewModel = loginViewModel, {
                // ForgotPasswordScreen to Previous Stack
                navController.navigateUp()
            }, { email, password, passwordConfirm ->
                // ForgotPasswordScreen to VerificationScreen
                navController.navigate("verification_screen/$email/$password/$passwordConfirm")
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
                // MainScreen to AboutScreen
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
            AboutScreen (
                loginViewModel = loginViewModel,
                mainViewModel = mainViewModel, {
                navController.navigateUp()
            }, {
                navController.navigate("landing_screen")
            }, {
                navController.navigate("verification_screen")
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