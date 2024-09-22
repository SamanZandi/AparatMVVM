package com.zandroid.aparatversion2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [VideoEntity::class], version = 5, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class VideoDb:RoomDatabase() {
    abstract fun dao():Dao

}