package com.jetpack.kawanusaha

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.jetpack.kawanusaha.main.*
import com.jetpack.kawanusaha.ui.pages.NavigationScreen
import com.jetpack.kawanusaha.ui.theme.KawanUsahaTheme

//https://developer.android.com/studio/write/app-link-indexing
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences: SharedPreferences =
            getSharedPreferences("SESSION", Context.MODE_PRIVATE)

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
                    )[LikeViewModel::class.java] // sementara
                    NavigationScreen(
                        loginViewModel = loginViewModel,
                        mainViewModel = mainViewModel,
                        likeViewModel = likeViewModel // sementara, ke navigation screen
                    )
                }
            }
        }
    }
}

