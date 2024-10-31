package com.zandroid.aparatversion2.data.model.register


import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @SerializedName("code")
    val code: Int?
)