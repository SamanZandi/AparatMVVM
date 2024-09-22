package com.zandroid.aparatversion2.utils.network

import retrofit2.Response

open class NetworkResponse<T>(private val response: Response<T>) {

    fun generalNetworkResponse(): NetworkRequest<T> {
        return when {
            response.message().contains("timeout") -> NetworkRequest.Error("Timeout")
            response.code() == 401 -> NetworkRequest.Error("You are not authorized")
            response.code() == 500 -> NetworkRequest.Error("Try again")
            response.isSuccessful -> NetworkRequest.Success(response.body()!!)
            else -> NetworkRequest.Error(response.message())
        }
    }
}