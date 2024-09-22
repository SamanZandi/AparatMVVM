package com.zandroid.aparatversion2.utils.di

import android.content.Context
import androidx.room.Room
import com.zandroid.aparatversion2.data.database.VideoDb
import com.zandroid.aparatversion2.data.database.VideoEntity
import com.zandroid.aparatversion2.utils.VIDEO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)= Room
        .databaseBuilder(context, VideoDb::class.java, VIDEO_DATABASE)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db:VideoDb)=db.dao()


}