package com.zandroid.aparatversion2.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository):ViewModel(){
    val searchListLiveData=MutableLiveData<List<ResponseVideoList.ResponseVideoListItem>>()
    val loading=MutableLiveData<Boolean>(false)
    //check list empty or not!
    val empty=MutableLiveData<Boolean>()

    val hasNet=MutableLiveData<Boolean>()


    fun searchVideo(title:String)=viewModelScope.launch(Dispatchers.IO) {
        try {
            loading.postValue(true)
            val response=repository.searchVideo(title)
            if (response.isSuccessful) {
                hasNet.postValue(true)
                when (response.code()) {
                    in 200..202 -> {
                        if (response.body()!!.isNotEmpty()){
                            loading.postValue(false)
                            searchListLiveData.postValue(response.body())
                            empty.postValue(false)
                        }else{
                            empty.postValue(true)
                            searchListLiveData.postValue(emptyList())
                        }

                    }
                }
            }

        }catch (e: Exception) {
            hasNet.postValue(false)
        }
        loading.postValue(false)
    }



}