package com.jetpack.kawanusaha.main

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jetpack.kawanusaha.data.*
import com.jetpack.kawanusaha.db.DbData
import com.jetpack.kawanusaha.db.DbRepository
import com.jetpack.kawanusaha.db.fav.ItemFav
import com.jetpack.kawanusaha.`in`.Injection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val dataRepository: DataRepository,
    private val preferences: SharedPreferences,
    private val localRepository: DbRepository
) : ViewModel() {
    private val _userProfile = MutableStateFlow<ProfileResponse?>(null)
    val userProfile: StateFlow<ProfileResponse?> = _userProfile

    private val _status = MutableStateFlow(false)
    val status: StateFlow<Boolean> = _status

    private val _listUsaha = MutableStateFlow<UsahaResponse?>(null)
    val listUsaha: StateFlow<UsahaResponse?> = _listUsaha

    private val _usahaDetail = MutableStateFlow<UsahaDetailResponse?>(null)
    val usahaDetail: StateFlow<UsahaDetailResponse?> = _usahaDetail

    private val _articleDetail = MutableStateFlow<ArticleDetail?>(null)
    val articleDetail: StateFlow<ArticleDetail?> = _articleDetail

    init {
        clearStatus()
    }

    private fun getToken(): String = preferences.getString(TOKEN, "").toString()

    // User Profile
    fun getUser() {
        viewModelScope.launch {
            _userProfile.value = dataRepository.getUser(getToken())
        }
    }

    fun saveProfileChange(name: String, email: String) {
        viewModelScope.launch {
            _status.value = dataRepository.updateProfile(
                getToken(),
                ProfileRequest(name = name, email = email)
            )?.success ?: false
        }
    }

    // Usaha
    fun getListUsaha() {
        getToken()
        viewModelScope.launch {
            _listUsaha.value = dataRepository.getListUsaha(getToken())
        }
    }

    fun getUsahaDetail(id: Int) {
        viewModelScope.launch {
            _usahaDetail.value = dataRepository.getUsahaDetail(getToken(), id)
        }
    }

    fun createUsaha(usahaName: String, type: Int, tags: List<Tag>) {
        viewModelScope.launch {
            _status.value = dataRepository.createUsaha(
                getToken(),
                usahaRequest = UsahaRequest(usaha_name = usahaName, type = type, tags = tags)
            )?.success ?: false
        }
    }


    // Articles
    fun getAllArticles(): Flow<PagingData<ArticlesItem>> =
        dataRepository.getListArticle().cachedIn(viewModelScope)

    fun getUserArticles(): Flow<PagingData<ArticlesItem>> =
        dataRepository.getUserArticle(getToken()).cachedIn(viewModelScope)


    fun searchAllArticle(text: String): Flow<PagingData<ArticlesItem>> =
        dataRepository.searchAllArticle(text).cachedIn(viewModelScope)

    fun searchUserArticle(text: String): Flow<PagingData<ArticlesItem>> =
        dataRepository.searchUserArticle(getToken(), text).cachedIn(viewModelScope)


    fun getArticleDetail(id: Int) {
        viewModelScope.launch {
            _articleDetail.value = dataRepository.getArticleDetail(id)?.data
        }
    }

    fun createArticle(title: String, content: String, category: Int) {
        viewModelScope.launch {
            _status.value = dataRepository.createArticle(
                getToken(),
                CreateArticleRequest(
                    ArticleRequest(
                        title = title,
                        content = content,
                        image = "https://example.com/updated_image.jpg",
                        is_published = true
                    ),
                    category
                )

            )?.success ?: false
        }
    }

    fun clearStatus() {
        _status.value = false
    }


    fun getAllData(): LiveData<List<DbData>> = localRepository.getAllData()

    companion object {
        private const val TOKEN = "TOKEN"
    }
}

class MainViewModelFactory(
    private val preferences: SharedPreferences,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                Injection.provideRepository(),
                preferences,
                Injection.provideRepository(application)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}