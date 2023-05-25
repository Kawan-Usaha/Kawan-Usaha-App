package com.jetpack.kawanusaha.main

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jetpack.kawanusaha.data.DataRepository
import com.jetpack.kawanusaha.data.ProfileResponse
import com.jetpack.kawanusaha.`in`.Injection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val dataRepository: DataRepository, private val preferences: SharedPreferences) :
    ViewModel() {
    private var jwtToken: String = ""

    private val _userProfile = MutableStateFlow<ProfileResponse?>(null)
    val userProfile: StateFlow<ProfileResponse?> = _userProfile

    private fun getToken () {
        jwtToken = preferences.getString(TOKEN, "").toString()
    }

    fun getUser() {
        viewModelScope.launch {
            getToken()
            _userProfile.value = dataRepository.getUser(jwtToken)
        }
    }





    companion object {
        private const val TOKEN = "TOKEN"
    }
}

class MainViewModelFactory(
    private val context: Context,
    private val preferences: SharedPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideRepository(context), preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}