package com.zandroid.aparatversion2.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.databinding.FragmentFavoriteBinding
import com.zandroid.aparatversion2.databinding.FragmentSplashBinding
import com.zandroid.aparatversion2.ui.home.adapters.VideoAdapter
import com.zandroid.aparatversion2.utils.setupRecyclerView
import com.zandroid.aparatversion2.utils.showSnackBar
import com.zandroid.aparatversion2.utils.visible
import com.zandroid.aparatversion2.viewModel.FavoriteViewModel
import com.zandroid.aparatversion2.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    //Binding
    private var _binding: FragmentFavoriteBinding?= null
    private val binding get() = _binding!!

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    //Other
    private val viewModel:FavoriteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.getfavorites()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
          viewModel.favoriteList.observe(viewLifecycleOwner){
              favoriteAdapter.setData(it)
              favoriteList.setupRecyclerView(LinearLayoutManager(requireContext()),favoriteAdapter)

          }

            //Empty
            viewModel.empty.observe(viewLifecycleOwner){empty->
                if (empty){
                    emptyList.visible(true,favoriteList)
                }else{
                    emptyList.visible(false,favoriteList)
                }
            }

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem=menu.findItem(R.id.filter)
        menuItem?.isVisible=false
        super.onPrepareOptionsMenu(menu)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}