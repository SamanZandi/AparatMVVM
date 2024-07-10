package com.zandroid.aparatversion2.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


class ResponseVideoList : ArrayList<ResponseVideoList.ResponseVideoListItem>(){
    @Parcelize
    data class ResponseVideoListItem(
        @SerializedName("cat_id")
        val catId: String?, // 2
        @SerializedName("creator")
        val creator: String?, // 2
        @SerializedName("description")
        val description: String?, // Android JetPack tools for android developers
        @SerializedName("icon")
        val icon: String?, // http://androidsupport.ir/pack/aparat/images/jetpack.png
        @SerializedName("id")
        val id: String?, // 2
        @SerializedName("link")
        val link: String?, // http://androidsupport.ir/pack/aparat/videos/Android-Jetpack.mp4
        @SerializedName("special")
        val special: String?, // 1
        @SerializedName("time")
        val time: String?, // 2:14
        @SerializedName("title")
        val title: String?, // Android JetPack
        @SerializedName("view")
        val view: String? // 225
    ):Parcelable
}