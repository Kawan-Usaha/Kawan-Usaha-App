package com.jetpack.kawanusaha.db

import android.content.Context
import androidx.room.*

@Database(entities = [DbData::class], version = 1)
abstract class DataRoomDatabase : RoomDatabase(){
    abstract fun dataDao(): DataDAO
    companion object {
        @Volatile
        private var INSTANCE: DataRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): DataRoomDatabase {
            if (INSTANCE == null) {
                synchronized(DataRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DataRoomDatabase::class.java, "dbdata")
                        .build()
                }
            }
            return INSTANCE as DataRoomDatabase
        }
    }
}