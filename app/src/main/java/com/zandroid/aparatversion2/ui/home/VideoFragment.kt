package com.zandroid.aparatversion2.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.databinding.FragmentVideoBinding
import com.zandroid.aparatversion2.ui.home.adapters.VideoAdapter
import com.zandroid.aparatversion2.utils.CATEGORY_ID
import com.zandroid.aparatversion2.utils.FROM
import com.zandroid.aparatversion2.utils.TO
import com.zandroid.aparatversion2.utils.setupRecyclerView
import com.zandroid.aparatversion2.utils.visible
import com.zandroid.aparatversion2.viewModel.VideoByCatViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VideoFragment :  BottomSheetDialogFragment(){

    //Binding
    private var _binding:FragmentVideoBinding?=null
    private val binding get() = _binding!!

    @Inject
    lateinit var videoAdapter: VideoAdapter

    //Other
    private var catId=0
    private var from=0
    private var to=0
    private val viewModel:VideoByCatViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentVideoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catId=arguments?.getInt(CATEGORY_ID)?:0
        from=arguments?.getInt(FROM)?:0
        to=arguments?.getInt(TO)?:0

        binding.apply {
            //close
            btnClose.setOnClickListener { dismiss() }

            //Loading
            viewModel.loading.observe(viewLifecycleOwner){loading->
                if (loading){
                    homeLoading.visible(true,videoByCatList)
                }else{
                    homeLoading.visible(false,videoByCatList)
                }
            }

            //get data
            viewModel.loadVideoCategory(catId.toString(),from,to)
            viewModel.videoCatLiveData.observe(viewLifecycleOwner){
                videoAdapter.setData(it)
                videoByCatList.setupRecyclerView(LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL,false),videoAdapter)

                videoAdapter.setOnItemClickListener { video->
                    this@VideoFragment.dismiss()
                    val direction=VideoFragmentDirections.actionToDetail(video)
                    findNavController().navigate(direction)
                }
            }

            //show Empty
            viewModel.empty.observe(viewLifecycleOwner){empty->
                if (empty){
                    emptyLay.visible(true,videoByCatList)
                }else{
                    emptyLay.visible(false,videoByCatList)
                }
            }



        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}