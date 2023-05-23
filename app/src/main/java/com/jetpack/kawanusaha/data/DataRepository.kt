package com.jetpack.kawanusaha.data

import android.util.Log
import com.jetpack.kawanusaha.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(private val apiService: ApiService) {
    suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val client = apiService.login(loginRequest)
                val response = client.execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e(TAG, "On Failure: ${response.message()}")
                    null
                }
            } catch (t: Throwable) {
                Log.e(TAG, "On Failure: ${t.message}")
                null
            }
        }
    }

    suspend fun register(registerRequest: RegisterRequest): RegisterResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val client = apiService.register(registerRequest)
                val response = client.execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e(TAG, "On Failure: ${response.message()}")
                    null
                }
            } catch (t: Throwable) {
                Log.e(TAG, "On Failure: ${t.message}")
                null
            }
        }
    }

    companion object {
        private const val TAG = "DataRepository"
    }
}