package com.zandroid.aparatversion2.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zandroid.aparatversion2.utils.VIDEO_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVideo(video: VideoEntity)

    @Delete
    suspend fun deleteVideo(video: VideoEntity)


    @Query("SELECT * FROM $VIDEO_TABLE")
    fun getAllVideos(): MutableList<VideoEntity>




    @Query("SELECT EXISTS (SELECT 1 FROM $VIDEO_TABLE WHERE id=:id)")
    fun existVideo(id:Int):Boolean



}