package com.zandroid.aparatversion2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [VideoEntity::class], version = 2, exportSchema = false)
abstract class VideoDb:RoomDatabase() {
    abstract fun dao():Dao

}