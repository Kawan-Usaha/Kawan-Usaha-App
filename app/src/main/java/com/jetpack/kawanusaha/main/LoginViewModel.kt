package com.jetpack.kawanusaha.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jetpack.kawanusaha.data.*
import com.jetpack.kawanusaha.`in`.Injection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _loginCredential = MutableStateFlow<LoginResponse?>(null)
    val loginCredential: StateFlow<LoginResponse?> = _loginCredential

    private val _loginToken = MutableStateFlow("")
    val loginToken: StateFlow<String> = _loginToken

    private val _registerCredential = MutableStateFlow<RegisterResponse?>(null)
    val registerCredential: StateFlow<RegisterResponse?> = _registerCredential

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginCredential.value = dataRepository.login(
                loginRequest = LoginRequest(
                    email = email,
                    password = password
                )
            )
        }
    }

    fun register(name: String, email: String, password: String, passwordConfirm: String) {
        viewModelScope.launch {
            _registerCredential.value = dataRepository.register(
                registerRequest = RegisterRequest(
                    name = name,
                    email = email,
                    password = password,
                    password_confirm = passwordConfirm
                )
            )
        }
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