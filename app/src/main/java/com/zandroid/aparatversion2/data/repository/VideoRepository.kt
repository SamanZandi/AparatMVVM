package com.zandroid.aparatversion2.data.repository

import com.zandroid.aparatversion2.data.server.ApiServices
import javax.inject.Inject

class VideoRepository @Inject constructor(private val api: ApiServices) {
    suspend fun loadVideoCat(catId:String,from:Int,to:Int)=api.loadVideosByCategory(catId, from, to)

}
