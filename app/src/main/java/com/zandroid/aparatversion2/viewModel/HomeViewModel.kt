package com.zandroid.aparatversion2.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.zandroid.aparatversion2.data.model.ResponseCategory
import com.zandroid.aparatversion2.data.model.ResponseNews
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.data.repository.HomeRepository
import com.zandroid.aparatversion2.utils.network.CheckConnection
import com.zandroid.aparatversion2.utils.network.NetworkRequest
import com.zandroid.aparatversion2.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository:HomeRepository):ViewModel() {


    val newsListLiveData=MutableLiveData<NetworkRequest<List<ResponseNews.ResponseNewsItem>>>()
    val categoriesLiveData=MutableLiveData<NetworkRequest<List<ResponseCategory.ResponseCategoryItem>>>()
    val specialLiveData=MutableLiveData<NetworkRequest<List<ResponseVideoList.ResponseVideoListItem>>>()
    val bestLiveData=MutableLiveData<NetworkRequest<List<ResponseVideoList.ResponseVideoListItem>>>()
    val newLiveData=MutableLiveData<NetworkRequest<List<ResponseVideoList.ResponseVideoListItem>>>()



    fun loadNews()=viewModelScope.launch {
        newsListLiveData.value=NetworkRequest.Loading()
        val response=repository.loadNews()
        newsListLiveData.value=NetworkResponse(response).generalNetworkResponse()
        }



   fun loadCategories()=viewModelScope.launch {
       categoriesLiveData.value=NetworkRequest.Loading()
       val response=repository.loadCategories()
       categoriesLiveData.value=NetworkResponse(response).generalNetworkResponse()
    }

    fun loadSpecial()=viewModelScope.launch{
        specialLiveData.value=NetworkRequest.Loading()
        val response=repository.loadSpecial()
        specialLiveData.value=NetworkResponse(response).generalNetworkResponse()
    }

    fun loadBest()=viewModelScope.launch{
        bestLiveData.value=NetworkRequest.Loading()
        val response=repository.loadBest()
        bestLiveData.value=NetworkResponse(response).generalNetworkResponse()
    }

    fun loadNew()=viewModelScope.launch{
        newLiveData.value=NetworkRequest.Loading()
        val response=repository.loadNew()
        newLiveData.value=NetworkResponse(response).generalNetworkResponse()
    }

}