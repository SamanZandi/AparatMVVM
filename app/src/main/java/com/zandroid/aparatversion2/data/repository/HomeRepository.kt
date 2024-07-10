package com.zandroid.aparatversion2.data.repository

import com.zandroid.aparatversion2.data.server.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api:ApiServices) {
   suspend fun loadNews()=api.loadNews()
   suspend fun loadCategories()=api.loadCategories()
   suspend fun loadSpecial()=api.loadSpecial()
   suspend fun loadBest()=api.loadBest()
   suspend fun loadNew()=api.loadNew()

}