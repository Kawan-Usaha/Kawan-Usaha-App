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
    @ColumnInfo(name = "title_fav") var title: String? = null,
    @ColumnInfo(name = "content_fav") var content: String? = null,
    @ColumnInfo(name = "image_fav") var image: String? = null,
    @ColumnInfo(name = "is_published_fav") var is_published: Boolean = false,
    @ColumnInfo(name = "creation_time_fav") var createdAt: String? = null,
    @ColumnInfo(name = "article_id_fav") var articleId: Int? = null,
): Parcelable

@Entity
@Parcelize
data class ItemFav(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title_fav") var title: String? = null,
    @ColumnInfo(name = "content_fav") var content: String? = null,
    @ColumnInfo(name = "image_fav") var image: String? = null,
    @ColumnInfo(name = "is_published_fav") var is_published: Boolean = false,
    @ColumnInfo(name = "creation_time_fav") var createdAt: String? = null,
    @ColumnInfo(name = "article_id_fav") var articleId: Int? = null,
    @ColumnInfo(name = "id_list") var idList: Int? = null,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = false,
): Parcelable