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

    @Query("DELETE from dbfav WHERE article_id_fav = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * from dbfav ORDER BY id ASC")
    fun getAllData(): LiveData<List<DbFav>>

    @Query("SELECT * from dbfav WHERE article_id_fav = :id ORDER BY id ASC")
    fun getAllDataById(id: Int?): LiveData<List<DbFav>>
}

@Dao
interface ItemFavDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: ItemFav)

    @Update
    fun update(data: ItemFav)

    @Delete
    fun delete(data: ItemFav)

    @Query("DELETE from itemfav WHERE title_fav = :name")
    suspend fun deleteByName(name: String)

//    @Query("SELECT * from itemfav WHERE title_fav = :name")
//    suspend fun getFavoriteDataByName(name: String): LiveData<List<ItemFav>>

    @Query("SELECT * from itemfav WHERE id_list = :idList ORDER BY id ASC")
    fun getAllItemDataByListId(idList: Int?): LiveData<List<ItemFav>>
    @Query("SELECT * from itemfav ORDER BY id ASC")
    fun getAllItemData(): LiveData<List<ItemFav>>

}