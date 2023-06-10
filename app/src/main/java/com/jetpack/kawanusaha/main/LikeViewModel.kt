package com.jetpack.kawanusaha.main

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jetpack.kawanusaha.db.fav.DbFav
import com.jetpack.kawanusaha.db.fav.DbFavoriteRepository
import com.jetpack.kawanusaha.`in`.Injection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class ListArticle(
    val id: Int,
    val name: String,
    val description: String? = null,
)

class LikeViewModel(
    private val favoriteRepository: DbFavoriteRepository,
) : ViewModel()
{
    private val uiState = MutableStateFlow(ListArticleViewState())

    private val _itemsState = mutableStateListOf<DbFav>()

    private val _checkboxState = mutableStateMapOf<Int, MutableStateFlow<Boolean>>()

    init{
        fetchArticleListData()
    }

    private fun fetchArticleListData(){
        viewModelScope.launch {
            val test = ListArticle(1, "Example List", "Hello")
            val test2 = ListArticle(2, "Example List 2", "Hello")
            val listArticle = listOf(test, test2)

            uiState.value = uiState.value.copy(isLoading = false, listArticles = listArticle)
        }
    }

    fun insert(data: DbFav){
        favoriteRepository.insert(data)
    }

    fun delete(data: DbFav){
        favoriteRepository.delete(data)
    }


    val allData: LiveData<List<DbFav>> = favoriteRepository.getAllDbFavData()

    fun getAllDataById(id: Int?): LiveData<List<DbFav>>{
        return favoriteRepository.getAllDataById(id)
    }
    fun deleteById(id: Int) = viewModelScope.launch { favoriteRepository.deleteById(id)}


}

data class ListArticleViewState(val isLoading: Boolean = true, val listArticles: List<ListArticle> = emptyList())

sealed class ListArticleViewEvent{
    data class AddItem(val listArticle: ListArticle) : ListArticleViewEvent()
    data class RemoveItem(val listArticle: ListArticle): ListArticleViewEvent()
}

class LikeViewModelFactory(
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LikeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LikeViewModel(Injection.provideFavRepository(application)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}