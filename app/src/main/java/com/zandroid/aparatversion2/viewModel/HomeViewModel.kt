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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository:HomeRepository):ViewModel() {


    val newsListLiveData=MutableLiveData<List<ResponseNews.ResponseNewsItem>>()
    val categoriesLiveData=MutableLiveData<List<ResponseCategory.ResponseCategoryItem>>()
    val specialLiveData=MutableLiveData<List<ResponseVideoList.ResponseVideoListItem>>()
    val bestLiveData=MutableLiveData<List<ResponseVideoList.ResponseVideoListItem>>()
    val newLiveData=MutableLiveData<List<ResponseVideoList.ResponseVideoListItem>>()
    val loading=MutableLiveData<Boolean>()
    val hasNet=MutableLiveData<Boolean>()


    fun loadNews()=viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)

      try {
           val response=repository.loadNews()

          if (response.isSuccessful) {
             hasNet.postValue(true)
              when (response.code()) {
                  in 200..202 -> {
                      loading.postValue(false)
                      newsListLiveData.postValue(response.body())
                  }
              }
          }
        }catch (e: Exception){
            hasNet.postValue(false)

        }
        loading.postValue(false)

        }



   fun loadCategories()=viewModelScope.launch(Dispatchers.IO) {
       loading.postValue(true)
       try {

           val response=repository.loadCategories()
           if (response.isSuccessful) {
              hasNet.postValue(true)
               when (response.code()) {
                   in 200..202 -> {
                       loading.postValue(false)
                       categoriesLiveData.postValue(response.body())
                   }
               }
           }
       }catch (e:Exception){
          hasNet.postValue(false)
       }
       loading.postValue(false)

    }

    fun loadSpecial()=viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        try {

            val response=repository.loadSpecial()
            if (response.isSuccessful) {
                hasNet.postValue(true)
                when (response.code()) {
                    in 200..202 -> {
                        loading.postValue(false)
                        specialLiveData.postValue(response.body())
                    }
                }
            }
        }catch (e:Exception){
         hasNet.postValue(false)
        }
        loading.postValue(false)
    }

    fun loadBest()=viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        try {

            val response=repository.loadBest()
            if (response.isSuccessful) {
               hasNet.postValue(true)
                when (response.code()) {
                    in 200..202 -> {
                        loading.postValue(false)
                        bestLiveData.postValue(response.body())
                    }
                }
            }
        }catch (e:Exception){
           hasNet.postValue(false)
        }
        loading.postValue(false)

    }

    fun loadNew()=viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        try {

            val response=repository.loadNew()
            if (response.isSuccessful) {
               hasNet.postValue(true)
                when (response.code()) {
                    in 200..202 -> {
                        loading.postValue(false)
                        newLiveData.postValue(response.body())
                    }
                }
            }
        }catch (e:Exception){
           hasNet.postValue(false)
        }
        loading.postValue(false)

    }

}