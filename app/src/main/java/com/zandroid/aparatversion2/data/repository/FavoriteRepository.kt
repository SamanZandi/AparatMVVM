package com.zandroid.aparatversion2.data.repository

import com.zandroid.aparatversion2.data.database.Dao
import com.zandroid.aparatversion2.data.database.VideoEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class FavoriteRepository @Inject constructor(private val dao: Dao) {

 fun getAllFavorites()=dao.getAllVideos()
}