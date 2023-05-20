package com.jetpack.kawanusaha.data

import com.jetpack.kawanusaha.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DataRepository (private val apiService: ApiService) {
    fun login(loginRequest: LoginRequest): () -> LoginResponse {
        return {
            val client = apiService.login(loginRequest)
            val response = client.execute()
            if (response.isSuccessful) {
                response.body()
                    ?: throw IllegalStateException("Pokemon detail response body is null")
            } else {
                throw HttpException(response)
            }
        }
    }

    fun register(registerRequest: RegisterRequest): () -> RegisterResponse {
        return {
            val client = apiService.register(registerRequest)
            val response = client.execute()
            if (response.isSuccessful) {
                response.body()
                    ?: throw IllegalStateException("Pokemon detail response body is null")
            } else {
                throw HttpException(response)
            }
        }
    }


}