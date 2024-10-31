package com.zandroid.aparatversion2.data.server

import com.zandroid.aparatversion2.data.model.ResponseCategory
import com.zandroid.aparatversion2.data.model.ResponseNews
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.data.model.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {

    @POST("register.php")
    @FormUrlEncoded
    suspend fun registerUser(@Field("username") username: String,
                             @Field("password") password: String ):Response<ResponseRegister>

    @POST("login.php")
    @FormUrlEncoded
    suspend fun loginUser(@Field("username") username: String,
                             @Field("password") password: String ):Response<ResponseRegister>

    @GET("getNews.php")
    suspend fun loadNews():Response<List<ResponseNews.ResponseNewsItem>>


    @GET("getCategory.php")
    suspend fun loadCategories():Response<List<ResponseCategory.ResponseCategoryItem>>

    @GET("getSpecial.php")
    suspend fun loadSpecial():Response<List<ResponseVideoList.ResponseVideoListItem>>

    @GET("getBestVideos.php")
    suspend fun loadBest():Response<List<ResponseVideoList.ResponseVideoListItem>>

    @GET("getNewVideos.php")
    suspend fun loadNew():Response<List<ResponseVideoList.ResponseVideoListItem>>

    @GET("search.php")
    suspend fun searchVideo(@Query("title") search:String):Response<List<ResponseVideoList.ResponseVideoListItem>>

    @POST("getVideosCategory.php")
    @FormUrlEncoded
    suspend fun loadVideosByCategory(@Field("catId") catId: String,
                                     @Field("from") from: Int,
                                     @Field("to") to: Int):Response<List<ResponseVideoList.ResponseVideoListItem>>

}