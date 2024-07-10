package com.zandroid.aparatversion2.data.repository

import com.zandroid.aparatversion2.data.database.Dao
import com.zandroid.aparatversion2.data.database.VideoEntity
import javax.inject.Inject

class DetailRepository @Inject constructor(private val dao: Dao) {

    suspend fun saveVideo(video:VideoEntity)=dao.saveVideo(video)
    suspend fun deleteVideo(video:VideoEntity)=dao.deleteVideo(video)
    fun exists(id:Int)=dao.existVideo(id)
}