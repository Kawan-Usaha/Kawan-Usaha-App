package com.jetpack.kawanusaha.db

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DbRepository (application: Application){
    private val mDataDao: DataDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = DataRoomDatabase.getDatabase(application)
        mDataDao = db.dataDao()
    }
    fun getAllData(): LiveData<List<DbData>> = mDataDao.getAllData()
    fun insert(data: DbData) {
        executorService.execute { mDataDao.insert(data) }
    }
    fun delete(data: DbData) {
        executorService.execute { mDataDao.delete(data) }
    }
    fun delete(id: Int){
        executorService.execute { mDataDao.delete(id) }
    }
    fun update(data: DbData) {
        executorService.execute { mDataDao.update(data) }
    }
}