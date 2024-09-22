package com.zandroid.aparatversion2.data.database


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zandroid.aparatversion2.data.model.ResponseVideoList

class TypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun videoToJson(recipe: ResponseVideoList.ResponseVideoListItem): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToVideo(data: String): ResponseVideoList.ResponseVideoListItem {
        return gson.fromJson(data, ResponseVideoList.ResponseVideoListItem::class.java)
    }

}