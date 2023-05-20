package com.jetpack.kawanusaha.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jetpack.kawanusaha.data.DataRepository
import com.jetpack.kawanusaha.data.LoginRequest
import com.jetpack.kawanusaha.data.RegisterRequest
import com.jetpack.kawanusaha.`in`.Injection

class LoginViewModel (private val dataRepository: DataRepository) : ViewModel() {
    fun login (email : String, password: String){
        dataRepository.login(LoginRequest(email = email, password = password))
    }

    fun register (name: String, email: String, password: String, password_confirm: String){
        dataRepository.register(RegisterRequest(name = name, email = email, password = password, password_confirm = password_confirm))
    }
}


class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}