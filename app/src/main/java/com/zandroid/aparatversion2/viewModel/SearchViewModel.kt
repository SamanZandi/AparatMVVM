package com.zandroid.aparatversion2.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.data.repository.SearchRepository
import com.zandroid.aparatversion2.utils.network.NetworkRequest
import com.zandroid.aparatversion2.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository):ViewModel(){
    val searchListLiveData=MutableLiveData<NetworkRequest<List<ResponseVideoList.ResponseVideoListItem>>>()

    fun searchVideo(title:String)=viewModelScope.launch {
       searchListLiveData.value=NetworkRequest.Loading()
       val response=repository.searchVideo(title)
        searchListLiveData.value=NetworkResponse(response).generalNetworkResponse()
    }



}