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
import com.jetpack.kawanusaha.main.LoginViewModel
import com.jetpack.kawanusaha.main.LoginViewModelFactory
import com.jetpack.kawanusaha.ui.NavigationScreen
import com.jetpack.kawanusaha.ui.theme.KawanUsahaTheme

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
                        LoginViewModelFactory(this, preferences)
                    )[LoginViewModel::class.java]
                    NavigationScreen(loginViewModel = loginViewModel)
                }
            }
        }
    }
}

