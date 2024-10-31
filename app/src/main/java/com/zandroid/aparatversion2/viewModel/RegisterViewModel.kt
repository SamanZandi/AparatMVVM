package com.zandroid.aparatversion2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zandroid.aparatversion2.data.model.register.ResponseRegister
import com.zandroid.aparatversion2.data.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository): ViewModel(){

    val stateLiveData=MutableLiveData<ResponseRegister>()

    fun registerUser(username:String,password: String)=viewModelScope.launch {
        val response=repository.registerUser(username,password)
        stateLiveData.value=response.body()
    }

    fun loginUser(username:String,password: String)=viewModelScope.launch {
        val response=repository.loginUser(username,password)
        stateLiveData.value=response.body()
    }



    fun saveState(code: Int)=viewModelScope.launch {
        repository.saveState(code)
    }

    val getState=repository.getState.asLiveData()
}