package com.zandroid.aparatversion2.ui.detail.player

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer

import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.databinding.ActivityPlayerBinding
import com.zandroid.aparatversion2.databinding.FragmentDetailBinding
import com.zandroid.aparatversion2.utils.VIDEO_LINK
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    //Binding
    private var _binding: ActivityPlayerBinding? = null
    private val binding get() = _binding!!

    lateinit var player :ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

      binding.apply {
            val link=intent.getStringExtra(VIDEO_LINK)
           player = ExoPlayer.Builder(this@PlayerActivity).build()
            playerFullView.player = player
            // Build the media item.
            val mediaItem = MediaItem.fromUri(link!!)
            // Set the media item to be played.
            player.setMediaItem(mediaItem)
            // Prepare the player.
            player.prepare()
            player.playWhenReady=true
            // Start the playback.

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
        _binding = null
    }




}