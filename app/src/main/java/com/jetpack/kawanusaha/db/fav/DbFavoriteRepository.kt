package com.jetpack.kawanusaha.db.fav

import android.app.Application
import androidx.lifecycle.LiveData
import com.jetpack.kawanusaha.db.DataRoomDatabase
import com.jetpack.kawanusaha.db.DbData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DbFavoriteRepository(application: Application) {
    private val mFavDAO: FavDAO
    private val mItemFavDao: ItemFavDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = DataRoomDatabase.getDatabase(application)
        mFavDAO = db.favDao()
        mItemFavDao = db.itemFavDao()
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
    fun getAllItemFavData() = mItemFavDao.getAllItemData()
    fun getAllItemFavDataByListId(idList: Int?) = mItemFavDao.getAllItemDataByListId(idList)
    suspend fun deleteById(id: Int) = mFavDAO.deleteById(id)

    suspend fun deleteByName(name: String) = mItemFavDao.deleteByName(name)

    fun insertItem(data: ItemFav){
        executorService.execute { mItemFavDao.insert(data) }
    }

    fun getAllDataById(id: Int?) = mFavDAO.getAllDataById(id)


    fun updateItem(data: ItemFav){
        executorService.execute { mItemFavDao.update(data) }
    }

//    suspend fun getItemByName(name: String?) = mItemFavDao.getFavoriteDataByName(name)

    fun deleteItem(data: ItemFav){
        executorService.execute { mItemFavDao.delete(data) }
    }

}