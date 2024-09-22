package com.zandroid.aparatversion2.data.repository

import com.zandroid.aparatversion2.data.server.ApiServices
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class SearchRepository @Inject constructor(private val api: ApiServices) {
    suspend fun searchVideo(search:String)=api.searchVideo(search)


}