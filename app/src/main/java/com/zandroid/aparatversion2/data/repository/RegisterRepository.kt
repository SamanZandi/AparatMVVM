package com.zandroid.aparatversion2.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zandroid.aparatversion2.data.server.ApiServices
import com.zandroid.aparatversion2.utils.CODE
import com.zandroid.aparatversion2.utils.STORE_STATE
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityRetainedScoped
class RegisterRepository @Inject constructor(private val api: ApiServices
,@ApplicationContext private val context: Context) {

    suspend fun registerUser(username:String,password: String)=api.registerUser(username,password)
    suspend fun loginUser(username:String,password: String)=api.loginUser(username,password)

    companion object StoredKeys{
        val regCode= intPreferencesKey(CODE)
    }

    private val Context.dataStore:DataStore<Preferences> by preferencesDataStore(STORE_STATE)


    suspend fun saveState(code: Int){
        context.dataStore.edit {
            it[regCode]=code
        }

    }

    val getState = context.dataStore.data
        .map {
            it[regCode]?:0
    }


}