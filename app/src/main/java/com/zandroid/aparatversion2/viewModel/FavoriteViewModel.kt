package com.zandroid.aparatversion2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zandroid.aparatversion2.data.database.VideoEntity
import com.zandroid.aparatversion2.data.repository.FavoriteRepository
import com.zandroid.aparatversion2.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository): ViewModel(){
    val favoriteList=MutableLiveData<List<VideoEntity>>()
    val empty=MutableLiveData<Boolean>()

    fun getfavorites()=viewModelScope.launch {
        val response=repository.getAllFavorites()
        if (response.isNotEmpty()){
            favoriteList.postValue(response)
            empty.postValue(false)
        }else{
            favoriteList.postValue(emptyList())
            empty.postValue(true)
        }

    }
}