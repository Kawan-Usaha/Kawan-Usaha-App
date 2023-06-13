package com.jetpack.kawanusaha

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.jetpack.kawanusaha.main.*
import com.jetpack.kawanusaha.ui.pages.NavigationScreen
import com.jetpack.kawanusaha.ui.theme.KawanUsahaTheme
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//https://developer.android.com/studio/write/app-link-indexing
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences: SharedPreferences =
            getSharedPreferences("SESSION", Context.MODE_PRIVATE)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {

            KawanUsahaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val loginViewModel = ViewModelProvider(
                        this,
                        LoginViewModelFactory(preferences)
                    )[LoginViewModel::class.java]
                    val mainViewModel = ViewModelProvider(
                        this,
                        MainViewModelFactory(preferences, this.application)
                    )[MainViewModel::class.java]
                    val likeViewModel = ViewModelProvider(
                        this,
                        LikeViewModelFactory(this.application)
                    )[LikeViewModel::class.java]
                    NavigationScreen(
                        loginViewModel = loginViewModel,
                        mainViewModel = mainViewModel,
                        likeViewModel = likeViewModel,
                    )

                }
            }
        }

    }
}
