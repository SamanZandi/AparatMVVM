package com.zandroid.aparatversion2.utils.network

sealed class NetworkRequest<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : NetworkRequest<T>()
    class Success<T>(data: T) : NetworkRequest<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkRequest<T>(data, message)
}