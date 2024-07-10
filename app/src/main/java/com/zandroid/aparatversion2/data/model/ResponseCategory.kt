package com.zandroid.aparatversion2.data.model


import com.google.gson.annotations.SerializedName

class ResponseCategory : ArrayList<ResponseCategory.ResponseCategoryItem>(){
    data class ResponseCategoryItem(
        @SerializedName("description")
        val description: String?, // Technology categories.The prerequisite is that the technology innovation should support mitigation and adaptation of greenhouse gas (GHG) emissions directly or indirectly. The technology development must be from proof of concept stage to pre-commercialisation.
        @SerializedName("icon")
        val icon: String, // http://androidsupport.ir/pack/aparat/images/technology.jpg
        @SerializedName("id")
        val id: String?, // 1
        @SerializedName("title")
        val title: String? // Technology
    )
}