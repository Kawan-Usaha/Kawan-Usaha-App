package com.jetpack.kawanusaha.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.StateFlow

@Dao
interface DataDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: DbData)
    @Update
    fun update(data: DbData)
    @Delete
    fun delete(data: DbData)
    @Query("DELETE from dbdata WHERE article_id = :id")
    fun delete(id: Int)
    @Query("SELECT * from dbdata ORDER BY id ASC")
    fun getAllData(): LiveData<List<DbData>>
}