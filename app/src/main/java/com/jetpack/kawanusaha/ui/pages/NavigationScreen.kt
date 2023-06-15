package com.jetpack.kawanusaha.ui.pages

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jetpack.kawanusaha.data.GoogleAuthUiClient
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.main.MainViewModel
import com.jetpack.kawanusaha.ui.pages.authentication.*
import com.jetpack.kawanusaha.ui.pages.main.*

@Composable
fun NavigationScreen(
    loginViewModel: LoginViewModel,
    mainViewModel: MainViewModel,
    googleAuthUiClient: GoogleAuthUiClient
) {
    val navController = rememberNavController()
    val startDestination: String = "chat_screen"
//        if (loginViewModel.isLoggedIn()) "chat_screen" else "landing_screen"
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isKeyboardOpen by onKeyboardVisible()

    when (navBackStackEntry?.destination?.route) {
        "landing_screen" -> {
            bottomBarState.value = false
        }
        "forgot_password_screen" -> {
            bottomBarState.value = false
        }
        "login_screen" -> {
            bottomBarState.value = false
        }
        "register_screen" -> {
            bottomBarState.value = false
        }
        "verification_screen" -> {
            bottomBarState.value = false
        }
        "add_article_screen" -> {
            bottomBarState.value = false
        }
        "camera_screen" -> {
            bottomBarState.value = false
        }
        "chat_screen" -> {
            bottomBarState.value = !isKeyboardOpen
        }
        "explore_screen" -> {
            bottomBarState.value = !isKeyboardOpen
        }
        "article_screen/{id}" -> {
            bottomBarState.value = false
        }
        "change_about_screen" -> {
            bottomBarState.value = false
        }
        "about_developer_screen" -> {
            bottomBarState.value = false
        }
        else -> {
            bottomBarState.value = true
        }
    }

    Scaffold(
        bottomBar = { BottomBar(navController, bottomBarState) },
        modifier = Modifier
            .navigationBarsPadding()
            .systemBarsPadding()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        )
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
                LoginScreen(
                    viewModel = loginViewModel,
                    googleAuthUiClient = googleAuthUiClient,
                    {
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
                    navArgument("email") { type = NavType.StringType },
                    navArgument("password") { type = NavType.StringType },
                    navArgument("passwordConfirm") { type = NavType.StringType },
                ),
            ) {
                VerificationScreen(
                    viewModel = loginViewModel,
                    mainViewModel = mainViewModel,
                    email = it.arguments?.getString("email"),
                    password = it.arguments?.getString("password"),
                    passwordConfirm = it.arguments?.getString("passwordConfirm")
                ) {
                    navController.navigate("main_screen")
                }
            }

            // VerificationScreen Navigation
            composable(route = "verification_screen") {
                VerificationScreen(
                    viewModel = loginViewModel,
                    mainViewModel = mainViewModel,
                    email = null,
                    password = null,
                    passwordConfirm = null,
                ) {
                    navController.navigate("main_screen")
                }
            }

            // ForgotPassword Navigation
            composable(route = "forgot_password_screen") {
                ForgotPasswordScreen({
                    // ForgotPasswordScreen to Previous Stack
                    navController.navigateUp()
                }, { email, password, passwordConfirm ->
                    // ForgotPasswordScreen to VerificationScreen
                    navController.navigate("verification_screen/$email/$password/$passwordConfirm")
                })
            }

            // MainScreen Navigation
            composable(route = "main_screen") {
                MainScreen(
                    mainViewModel = mainViewModel, { id ->
                        // MainScreen to ArticleScreen
                        navController.navigate("article_screen/$id")
                    }, {
                        // MainScreen to AddArticleScreen
                        navController.navigate("add_article_screen")
                    })
            }

            composable(route = "chat_screen") {
                ChatScreen(mainViewModel)
            }

            composable(
                route = "article_screen/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                // Get Id From Argument
                it.arguments?.getInt("id")?.let { id ->
                    ArticleScreen(mainViewModel, articleId = id) {
                        // Back to Previous Stack
                        navController.navigateUp()
                    }
                }
            }

            composable(route = "about_screen") {
                AboutScreen(
                    loginViewModel = loginViewModel,
                    mainViewModel = mainViewModel, { id ->
                        navController.navigate("article_screen/$id")
                    }, {
                        navController.navigate("landing_screen")
                    }, {
                        navController.navigate("verification_screen")
                    }, {
                        navController.navigate("change_about_screen")
                    }, { id ->
                        navController.navigate("usaha_detail_screen/$id")
                    }, {
                        navController.navigate("add_usaha_screen")
                    }, {
                        navController.navigate("about_developer_screen")
                    })
            }

            composable(route = "change_about_screen") {
                ChangeAboutScreen(mainViewModel = mainViewModel, {
                    navController.navigate("camera_screen")
                }) {
                    navController.navigateUp()
                }
            }

            composable(route = "add_usaha_screen") {
                AddUsahaScreen(mainViewModel = mainViewModel) {
                    navController.navigateUp()
                }
            }

            composable(route = "add_article_screen") {
                AddArticleScreen(
                    mainViewModel = mainViewModel,
                    navigateToMain = { navController.navigateUp() },
                    navToCamera = { navController.navigate("camera_screen") },
                    navBack = { navController.navigateUp() }
                )
            }

            composable(route = "like_screen") {
                LikeScreen(
                    { param1 ->
                        navController.navigate("article_screen/$param1")
                    }, mainViewModel = mainViewModel
                )
            }

            composable(
                route = "usaha_detail_screen/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                // Get Id From Argument
                it.arguments?.getInt("id")?.let { id ->
                    UsahaDetailScreen(mainViewModel, usahaId = id) {
                        // Back to Previous Stack
                        navController.navigateUp()
                    }
                }
            }

            composable(route = "explore_screen") {
                ExploreScreen(mainViewModel = mainViewModel,
                    navToAddArticle = { navController.navigate("add_article_screen") },
                    navToArticle = { id ->
                        navController.navigate("article_screen/$id")
                    })
            }

            composable(route = "camera_screen") {
                CameraScreen(mainViewModel = mainViewModel, navBack = {
                    navController.navigateUp()
                })
            }

            composable(route = "about_developer_screen"){
                AboutDeveloperScreen {
                    navController.navigateUp()
                }
            }
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

@Composable
fun onKeyboardVisible(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

