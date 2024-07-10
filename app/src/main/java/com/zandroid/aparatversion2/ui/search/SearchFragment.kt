package com.zandroid.aparatversion2.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zandroid.aparatversion2.R

import com.zandroid.aparatversion2.databinding.FragmentSearchBinding
import com.zandroid.aparatversion2.ui.home.HomeFragment2Directions
import com.zandroid.aparatversion2.ui.home.adapters.VideoAdapter
import com.zandroid.aparatversion2.utils.network.CheckConnection
import com.zandroid.aparatversion2.utils.setupRecyclerView
import com.zandroid.aparatversion2.utils.visible
import com.zandroid.aparatversion2.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    //Binding
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var videoAdapter: VideoAdapter

    @Inject
    lateinit var checkConnection: CheckConnection

    //Other
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding.apply {
            //checkInternet
            checkConnection.observe(viewLifecycleOwner){
                if (it){
                    showInternetStatus(true)
                }else{
                    showInternetStatus(false)
                }
                Log.e( "InternetStatus ", it.toString() )
            }

            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    searchLoading.visible(true, searchList)
                } else {
                    searchLoading.visible(false, searchList)
                }
            }

            //Search
            viewModel.searchListLiveData.observe(viewLifecycleOwner) {
                videoAdapter.setData(it)
                searchList.setupRecyclerView(LinearLayoutManager(requireContext()), videoAdapter)

                //click
                videoAdapter.setOnItemClickListener { video ->
                    val direction = SearchFragmentDirections.actionToDetail(video)
                    findNavController().navigate(direction)
                }
            }
            //Empty
            viewModel.empty.observe(viewLifecycleOwner) { empty ->
                if (empty) {
                    emptyList.visible(true, searchList)
                } else {
                    emptyList.visible(false, searchList)
                }
            }

            searchEdt.addTextChangedListener {
                if (it.toString().length > 2) {
                    viewModel.searchVideo(it.toString())
                } else {
                    emptyList.visible(true, searchList)
                }
            }



        }

    }
    private fun showInternetStatus(hasNet:Boolean){
        binding.apply {
            if (!hasNet){
                emptyLay.emptyImg.setImageResource(R.drawable.disconnect_internet)
                emptyLay.emptyImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
                emptyLay.emptyTxt.setText(getString(R.string.please_check_internet))
                emptyLay.emptyTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                emptyList.visible(true, searchList)
            }else{
                emptyList.visible(false, searchList)
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