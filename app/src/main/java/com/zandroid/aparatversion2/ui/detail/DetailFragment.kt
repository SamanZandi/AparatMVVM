package com.zandroid.aparatversion2.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.data.database.VideoEntity
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.databinding.FragmentDetailBinding
import com.zandroid.aparatversion2.databinding.FragmentHome2Binding
import com.zandroid.aparatversion2.ui.detail.player.PlayerActivity
import com.zandroid.aparatversion2.utils.VIDEO_LINK
import com.zandroid.aparatversion2.utils.showSnackBar
import com.zandroid.aparatversion2.viewModel.VideoDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {

    //Binding
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    //Other
    private val viewModel:VideoDetailViewModel by viewModels()
    private val args:DetailFragmentArgs by navArgs()
    private var isFavorite=false
    private var data by Delegates.notNull<ResponseVideoList.ResponseVideoListItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data= args.video!!
        binding.apply {
            //back
            btnBack.setOnClickListener { findNavController().navigateUp() }
            //load details
            initDetailData()
        }
    }

    private fun initDetailData(){
        binding.apply {

            //Favorite
            val videoEntity=VideoEntity(data.id!!.toInt(),data)

            //Load Images
            imgIconBig.load(data.icon){
                crossfade(true)
                crossfade(500)
            }
            imgIcon.load(data.icon){
                crossfade(true)
                crossfade(500)
            }

            txtTitle.text=data.title
            txtDesc.text=data.description
            txtDuration.text=data.time
            txtViews.text=data.view

            if (data.special=="1"){
                txtSpecial.setTextColor(ContextCompat.getColor(requireContext(),R.color.pink))
            }else{
                txtSpecial.setTextColor(ContextCompat.getColor(requireContext(),R.color.philippineSilver))
            }

            //go to player page
            btnPlay.setOnClickListener {
                val intent=Intent(activity,PlayerActivity::class.java)
                intent.putExtra(VIDEO_LINK,data.link)
                startActivity(intent)
            }


            viewModel.existsVideo(data.id!!.toInt())
            viewModel.existLiveData.observe(viewLifecycleOwner){
                isFavorite=it
                if (isFavorite){
                    btnFav.setColorFilter(ContextCompat.getColor(requireContext(),R.color.pink))
                }else{
                    btnFav.setColorFilter(ContextCompat.getColor(requireContext(),R.color.whiteSmoke))
                }
            }

            //click save,delete
            btnFav.setOnClickListener {
                if (isFavorite){
                    isFavorite=false
                    viewModel.deleteVideo(videoEntity)
                    btnFav.setColorFilter(ContextCompat.getColor(requireContext(),R.color.whiteSmoke))
                    it.showSnackBar("${data.title} has removed from favorite list",R.color.red)

                }else{
                    isFavorite=true
                   viewModel.saveVideo(videoEntity)
                    btnFav.setColorFilter(ContextCompat.getColor(requireContext(),R.color.pink))
                    it.showSnackBar("${data.title} has enterd favorite list :)", R.color.green)
                }
            }
        }


    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}