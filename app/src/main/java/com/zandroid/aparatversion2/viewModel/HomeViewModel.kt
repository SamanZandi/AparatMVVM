package com.zandroid.aparatversion2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zandroid.aparatversion2.data.model.ResponseCategory
import com.zandroid.aparatversion2.data.model.ResponseNews
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.data.repository.HomeRepository
import com.zandroid.aparatversion2.utils.BEST
import com.zandroid.aparatversion2.utils.NEW
import com.zandroid.aparatversion2.utils.SPECIAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository:HomeRepository):ViewModel() {


    val newsListLiveData=MutableLiveData<List<ResponseNews.ResponseNewsItem>>()
    val categoriesLiveData=MutableLiveData<List<ResponseCategory.ResponseCategoryItem>>()
    val specialLiveData=MutableLiveData<List<ResponseVideoList.ResponseVideoListItem>>()
    val bestLiveData=MutableLiveData<List<ResponseVideoList.ResponseVideoListItem>>()
    val newLiveData=MutableLiveData<List<ResponseVideoList.ResponseVideoListItem>>()
    val loading=MutableLiveData<Boolean>()


    init {
          loadCategories()
        loadNews()
    }


    fun loadNews()=viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        when (repository.loadNews().code()){
            in 200..202->{
                loading.postValue(false)
                newsListLiveData.postValue(repository.loadNews().body())}
        }
    }



   fun loadCategories()=viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        when (repository.loadCategories().code()){
            in 200..202->{
                loading.postValue(false)
                categoriesLiveData.postValue(repository.loadCategories().body())}
        }
    }

    fun loadSpecial()=viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        when (repository.loadSpecial().code()){
            in 200..202->{
                loading.postValue(false)
                specialLiveData.postValue(repository.loadSpecial().body())}
        }
    }

    fun loadBest()=viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        when (repository.loadBest().code()){
            in 200..202->{
                loading.postValue(false)
                bestLiveData.postValue(repository.loadBest().body())}
        }
    }

    fun loadNew()=viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        when (repository.loadNew().code()){
            in 200..202->{
                loading.postValue(false)
                newLiveData.postValue(repository.loadNew().body())}
        }
    }


}