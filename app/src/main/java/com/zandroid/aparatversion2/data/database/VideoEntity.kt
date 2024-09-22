package com.zandroid.aparatversion2.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.utils.VIDEO_TABLE
import kotlinx.parcelize.Parcelize

@Entity(tableName = VIDEO_TABLE)
data class VideoEntity(
    @PrimaryKey(autoGenerate = false)
    var id:Int=0,
    var response:ResponseVideoList.ResponseVideoListItem

)
