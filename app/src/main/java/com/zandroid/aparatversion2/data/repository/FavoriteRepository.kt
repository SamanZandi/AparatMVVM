package com.zandroid.aparatversion2.data.repository

import com.zandroid.aparatversion2.data.database.Dao
import com.zandroid.aparatversion2.data.database.VideoEntity
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao: Dao) {

 fun getAllFavorites()=dao.getAllVideos()
}