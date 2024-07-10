package com.zandroid.aparatversion2.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zandroid.aparatversion2.utils.VIDEO_TABLE
import kotlinx.parcelize.Parcelize

@Entity(tableName = VIDEO_TABLE)
@Parcelize
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var title:String="",
    var icon:String="",
    var view:String="",
    var time:String=""

):Parcelable
