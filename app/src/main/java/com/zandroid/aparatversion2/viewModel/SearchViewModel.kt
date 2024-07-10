package com.zandroid.aparatversion2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository):ViewModel(){
    val searchListLiveData=MutableLiveData<List<ResponseVideoList.ResponseVideoListItem>>()
    val loading=MutableLiveData<Boolean>(false)
    //check list empty or not!
    val empty=MutableLiveData<Boolean>()


    fun searchVideo(title:String)=viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        val response=repository.searchVideo(title)
        if (response.isSuccessful){
            if (response.body()!!.isNotEmpty()){
                    searchListLiveData.postValue(response.body())
                    empty.postValue(false)
            }else{
                searchListLiveData.postValue(emptyList())
                empty.postValue(true)
            }
        }
        loading.postValue(false)
    }



}