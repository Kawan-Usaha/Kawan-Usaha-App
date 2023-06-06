package com.jetpack.kawanusaha.db.fav

import android.app.Application
import androidx.lifecycle.LiveData
import com.jetpack.kawanusaha.db.DataRoomDatabase
import com.jetpack.kawanusaha.db.DbData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DbFavoriteRepository(application: Application) {
    private val mFavDAO: FavDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = DataRoomDatabase.getDatabase(application)
        mFavDAO = db.favDao()
    }

    fun insert(data: DbFav){
        executorService.execute { mFavDAO.insert(data) }
    }

    fun update(data: DbFav){
        executorService.execute { mFavDAO.update(data) }
    }

    fun delete(data: DbFav){
        executorService.execute { mFavDAO.delete(data) }
    }

    fun getAllDbFavData() = mFavDAO.getAllData()
    suspend fun deleteById(name: String) = mFavDAO.deleteById(name)
}