package com.jetpack.kawanusaha.data

import com.jetpack.kawanusaha.network.ApiService
import retrofit2.Call
import retrofit2.HttpException

class DataRepository (private val apiService: ApiService) {
    fun login(loginRequest: LoginRequest): Call<LoginResponse> {
        return apiService.login(loginRequest)
    }

    fun register(registerRequest: RegisterRequest): Call<RegisterResponse> {
        return apiService.register(registerRequest)
    }


}