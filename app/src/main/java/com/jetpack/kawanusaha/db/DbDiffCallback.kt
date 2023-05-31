package com.jetpack.kawanusaha.db

import androidx.recyclerview.widget.DiffUtil

class DataDiffCallback(private val mOldDataList: List<DbData>, private val mNewDataList: List<DbData>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldDataList.size
    }
    override fun getNewListSize(): Int {
        return mNewDataList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldDataList[oldItemPosition].id == mNewDataList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = mOldDataList[oldItemPosition]
        val newData = mNewDataList[newItemPosition]
        return oldData.articleId == newData.articleId
    }
}