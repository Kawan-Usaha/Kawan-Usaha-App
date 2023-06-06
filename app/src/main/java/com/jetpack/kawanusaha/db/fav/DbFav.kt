package com.jetpack.kawanusaha.db.fav

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class DbFav (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "list_name") val listName: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = false,
): Parcelable