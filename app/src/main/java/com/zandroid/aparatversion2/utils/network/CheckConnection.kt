package com.zandroid.aparatversion2.utils.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

class CheckConnection @Inject constructor(private val cm:ConnectivityManager,private val request: NetworkRequest):LiveData<Boolean>() {


    private val networkCallBack=object :ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            postValue(false)
        }


    }

    override fun onActive() {
        super.onActive()
     //  updateConnection()
        //اگر بالای 24 بود
        //register
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            cm.registerDefaultNetworkCallback(networkCallBack)
        }else{
            cm.registerNetworkCallback(request,networkCallBack)
        }
    }

    override fun onInactive() {
        super.onInactive()
        cm.unregisterNetworkCallback(networkCallBack)
    }

    private fun updateConnection() {
        val activeNetwork = cm.activeNetwork
        val networkCapabilities = cm.getNetworkCapabilities(activeNetwork)
        postValue(networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true)
    }

}