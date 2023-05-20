package com.jetpack.kawanusaha.`in`

import android.content.Context
import com.jetpack.kawanusaha.data.DataRepository
import com.jetpack.kawanusaha.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val apiService = ApiConfig.getApiService()
        return DataRepository(apiService)
    }
}