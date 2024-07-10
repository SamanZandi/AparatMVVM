package com.zandroid.aparatversion2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zandroid.aparatversion2.data.database.VideoEntity
import com.zandroid.aparatversion2.data.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @Inject constructor(private val repository: DetailRepository):ViewModel() {

    val existLiveData= MutableLiveData<Boolean>()

    fun saveVideo(video: VideoEntity)=viewModelScope.launch(Dispatchers.IO) {
        repository.saveVideo(video)
    }

    fun deleteVideo(video: VideoEntity)=viewModelScope.launch(Dispatchers.IO) {
        repository.deleteVideo(video)
    }


    fun existsVideo(id:Int)=viewModelScope.launch(Dispatchers.IO) {
        existLiveData.postValue(repository.exists(id))
    }


}