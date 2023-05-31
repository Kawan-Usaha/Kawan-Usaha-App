package com.jetpack.kawanusaha.`in`

import android.app.Application
import com.jetpack.kawanusaha.data.DataRepository
import com.jetpack.kawanusaha.db.DbRepository
import com.jetpack.kawanusaha.network.ApiConfig

object Injection {
    fun provideRepository(): DataRepository {
        val apiService = ApiConfig.getApiService()
        return DataRepository(apiService)
    }

    fun provideRepository(application: Application): DbRepository {
        return DbRepository(application)
    }
}