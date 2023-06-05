package com.jetpack.kawanusaha.db.fav

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jetpack.kawanusaha.db.DbData

@Dao
interface FavDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: DbFav)

    @Update
    fun update(data: DbFav)

    @Delete
    fun delete(data: DbFav)

    @Query("DELETE from dbfav WHERE list_name = :name")
    suspend fun deleteById(name: String)

    @Query("SELECT * from dbfav ORDER BY id ASC")
    fun getAllData(): LiveData<List<DbFav>>
}