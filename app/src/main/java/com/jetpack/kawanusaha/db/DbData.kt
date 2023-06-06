package com.jetpack.kawanusaha.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class DbData (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "content") var content: String? = null,
    @ColumnInfo(name = "image") var image: String? = null,
    @ColumnInfo(name = "is_published") var is_published: Boolean = false,
    @ColumnInfo(name = "cration_time") var createdAt: String? = null,
    @ColumnInfo(name = "article_id") var articleId: Int? = null
) : Parcelable