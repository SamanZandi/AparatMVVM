package com.zandroid.aparatversion2.data.repository

import com.zandroid.aparatversion2.data.server.ApiServices
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: ApiServices) {
    suspend fun searchVideo(search:String)=api.searchVideo(search)


}