package com.jetpack.kawanusaha.`in`

import com.jetpack.kawanusaha.data.DataRepository
import com.jetpack.kawanusaha.network.ApiConfig

/**
 * A dependency injection object for providing instances of DataRepository.
 */
object Injection {
    /**
     * Provides an instance of DataRepository.
     * @return The DataRepository instance.
     */
    fun provideRepository(): DataRepository {
        val apiService = ApiConfig.getApiService()
        return DataRepository(apiService)
    }
}