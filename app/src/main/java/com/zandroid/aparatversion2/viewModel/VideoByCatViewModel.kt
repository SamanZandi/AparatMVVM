package com.zandroid.aparatversion2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.data.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoByCatViewModel @Inject constructor(private val repository: VideoRepository):ViewModel(){

    val videoCatLiveData=MutableLiveData<List<ResponseVideoList.ResponseVideoListItem>>()
    val empty=MutableLiveData<Boolean>()
    val loading=MutableLiveData<Boolean>()


    fun loadVideoCategory(catId:String,from:Int,to:Int) =viewModelScope.launch {
        val response=repository.loadVideoCat(catId,from,to)
        loading.postValue(true)
        if(response.isSuccessful){
            when(response.code()) {
                in 200..202 ->{
                    if (response.body()!!.isNotEmpty()) {
                        empty.postValue(false)
                        videoCatLiveData.postValue(response.body())
                    } else {
                        empty.postValue(true)
                    }
                }
            }

        }
        loading.postValue(false)
    }
}