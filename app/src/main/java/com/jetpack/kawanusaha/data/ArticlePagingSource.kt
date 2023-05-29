package com.jetpack.kawanusaha.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jetpack.kawanusaha.network.ApiService
import kotlinx.coroutines.delay
import java.net.SocketTimeoutException

class ArticlePagingSource(private val apiService: ApiService) : PagingSource<Int, ArticlesItem>() {
    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllArticles(page, params.loadSize)
            LoadResult.Page(
                data = responseData.data.articles,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.data.articles.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e(TAG, exception.toString())
            if (exception is SocketTimeoutException) {
                delay(5000) // 5 seconds delay
                return load(params)
            }
            return LoadResult.Error(exception)
        }
    }
    private companion object {
        private const val TAG = "ArticlePagingSource"
        const val INITIAL_PAGE_INDEX = 1
    }
}