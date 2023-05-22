package com.jetpack.kawanusaha.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jetpack.kawanusaha.data.*
import com.jetpack.kawanusaha.`in`.Injection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val dataRepository: DataRepository) : ViewModel() {
    // Check Status for Register
    private val _authStatus = MutableStateFlow<Boolean?>(null)
    val authStatus : StateFlow<Boolean?> = _authStatus

    private val _loginToken = MutableStateFlow<String?>(null)
    val loginToken : StateFlow<String?> = _loginToken

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow("")
    val message : StateFlow<String> = _message

    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = dataRepository.login(LoginRequest(email = email, password = password))
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                _message.value = response.message()
                if (response.isSuccessful) {
                    _loginToken.value = response.body()?.data?.token
                } else {
                    _authStatus.value = false
                    Log.e(TAG, "On Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
                Log.e(TAG, "On Failure : ${t.message}")
            }
        })
    }

    fun register(name: String, email: String, password: String, passwordConfirm: String){
        _isLoading.value = true
        val client = dataRepository.register(RegisterRequest(
            name = name,
            email = email,
            password = password,
            password_confirm = passwordConfirm
        ))

        client.enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _authStatus.value = response.body()?.success ?: false
                } else {
                    _authStatus.value = false
                    Log.e(TAG, "On Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "On Failure : ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "Login View Model"
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