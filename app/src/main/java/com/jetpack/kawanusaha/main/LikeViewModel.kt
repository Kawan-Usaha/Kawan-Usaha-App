package com.jetpack.kawanusaha.main

import android.app.Application
import android.content.SharedPreferences
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jetpack.kawanusaha.db.fav.DbFav
import com.jetpack.kawanusaha.db.fav.DbFavoriteRepository
import com.jetpack.kawanusaha.db.fav.ItemFav
import com.jetpack.kawanusaha.`in`.Injection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
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
    val itemsState: List<DbFav> = _itemsState
    fun consumableState() = uiState.asStateFlow()

    private val _checkboxState = mutableStateMapOf<Int, MutableStateFlow<Boolean>>()


    fun getCheckboxState(id: Int): StateFlow<Boolean> {
        val checkboxState = _checkboxState.getOrPut(id) {MutableStateFlow(false)}
        return checkboxState.asStateFlow()
    }

    fun setCheckboxState(newState: Boolean, id: Int){
        val checkboxState = _checkboxState.getOrPut(id){ MutableStateFlow(false)}
        checkboxState.value = newState
    }
    init{
        fetchArticleListData()
    }

    fun handleViewEvent(viewEvent: ListArticleViewEvent){
        when(viewEvent){
            is ListArticleViewEvent.AddItem -> {
                val currentState = uiState.value
                val items = currentState.listArticles.toMutableList().apply {
                    add(viewEvent.listArticle)
                }
                uiState.value = uiState.value.copy(listArticles = items)
            }
            is ListArticleViewEvent.RemoveItem -> {}
        }
    }

    private fun fetchArticleListData(){
        viewModelScope.launch {
            val test = ListArticle(1, "Example List", "Hello")
            val test2 = ListArticle(2, "Example List 2", "Hello")
            val listArticle = listOf(test, test2)

            uiState.value = uiState.value.copy(isLoading = false, listArticles = listArticle)
        }
    }

//    fun add(title: String, description: String?){
//        favoriteRepository.insert(
//            data = DbFav(
//                listName = title,
//                description = description,
//            ))
//    }

    fun getListId(id: Int){
       return favoriteRepository.insertItem(
            data = ItemFav(
                idList = id
            )
        )
    }
    fun insertItem(title: String?, content: String?, createdAt: String?, idList: Int){
        favoriteRepository.insertItem(
            data = ItemFav(
                title = title,
                content = content,
                createdAt = createdAt,
                idList = idList,
            )
        )
    }

    fun insert(data: DbFav){
        favoriteRepository.insert(data)
    }

    fun delete(data: DbFav){
        favoriteRepository.delete(data)
    }

    fun deleteItem(data: ItemFav){
        favoriteRepository.deleteItem(data)
    }


    val allData: LiveData<List<DbFav>> = favoriteRepository.getAllDbFavData()
    val allItemData: LiveData<List<ItemFav>> = favoriteRepository.getAllItemFavData()
    fun allItemDataByListId(idList: Int?): LiveData<List<ItemFav>>{
       return favoriteRepository.getAllItemFavDataByListId(idList)
    }

    fun getAllDataById(id: Int?): LiveData<List<DbFav>>{
        return favoriteRepository.getAllDataById(id)
    }
    fun deleteById(id: Int) = viewModelScope.launch { favoriteRepository.deleteById(id)}

//    fun getItemByName(name:String?) = viewModelScope.launch { favoriteRepository.getItemByName(name) }

    fun deleteItemByName(name: String) = viewModelScope.launch { favoriteRepository.deleteByName(name) }
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