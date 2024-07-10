package com.zandroid.aparatversion2.data.model


import com.google.gson.annotations.SerializedName

class ResponseNews : ArrayList<ResponseNews.ResponseNewsItem>(){
    data class ResponseNewsItem(
        @SerializedName("icon")
        val icon: String?, // http://androidsupport.ir/pack/aparat/images/Sports.jpg
        @SerializedName("id")
        val id: String?, // 1
        @SerializedName("link")
        val link: String?, // https://cafebazaar.ir/app/com.tgbsco.medal
        @SerializedName("title")
        val title: String?, // Football
        @SerializedName("type")
        val type: String? // 1
    )
}