package com.zandroid.aparatversion2.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.muddassir.connection_checker.ConnectionState
import com.muddassir.connection_checker.checkConnection
import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.databinding.FragmentHome2Binding
import com.zandroid.aparatversion2.ui.home.adapters.CategoriesAdapter
import com.zandroid.aparatversion2.ui.home.adapters.NewsAdapter
import com.zandroid.aparatversion2.ui.home.adapters.VideoAdapter
import com.zandroid.aparatversion2.utils.CATEGORY_ID
import com.zandroid.aparatversion2.utils.FROM
import com.zandroid.aparatversion2.utils.TO
import com.zandroid.aparatversion2.utils.isNetworkAvailable
import com.zandroid.aparatversion2.utils.network.CheckConnection
import com.zandroid.aparatversion2.utils.network.NetworkRequest
import com.zandroid.aparatversion2.utils.setupRecyclerView
import com.zandroid.aparatversion2.utils.showSnackBar
import com.zandroid.aparatversion2.utils.visible
import com.zandroid.aparatversion2.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject
import kotlin.properties.Delegates


@AndroidEntryPoint
class HomeFragment2 : Fragment() {

    //Binding
    private var _binding: FragmentHome2Binding? = null
    private val binding get() = _binding!!

    //Adapters
    @Inject
    lateinit var newsAdapter: NewsAdapter

    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter

    @Inject
    lateinit var videoAdapter: VideoAdapter

    @Inject
    lateinit var checkConnection: CheckConnection

    //Other
    private val viewModel: HomeViewModel by viewModels()
    private val pagerSnapHelper: PagerSnapHelper by lazy { PagerSnapHelper() }
    private var isNetworkAvailable by Delegates.notNull<Boolean>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHome2Binding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //checkInternet
        initDisconnectInternet()
        checkConnection.observe(viewLifecycleOwner) { state ->
            isNetworkAvailable = state
            if (state) {
                showInternetStatus(true)
                loadNewsFromApi()
                loadCategoriesFromApi()
                loadSpecialFromApi()
            } else {
                showInternetStatus(false)

            }
        }
    }


    private fun loadNewsFromApi(){
        viewModel.loadNews()
        viewModel.newsListLiveData.observe(viewLifecycleOwner){response->

            when(response){
                is NetworkRequest.Loading->{

                }
                is NetworkRequest.Success->{
                    response.data.let {news->
                        newsAdapter.differ.submitList(news)
                        binding.newsList.setupRecyclerView(LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false), newsAdapter)
                        pagerSnapHelper.attachToRecyclerView(binding.newsList)
                        binding.indicator.attachToRecyclerView(binding.newsList, pagerSnapHelper)
                    }

                }
                is NetworkRequest.Error->{}
            }
        }
    }

    private fun loadCategoriesFromApi(){
        viewModel.loadCategories()
        viewModel.categoriesLiveData.observe(viewLifecycleOwner){response->
            when(response){
                is NetworkRequest.Loading->{
                    binding.homeLoading.visible(true,binding.categoriesList)
                }
                is NetworkRequest.Success->{
                    binding.homeLoading.visible(false,binding.categoriesList)
                    response.data.let {category->
                        categoriesAdapter.setData(category!!)
                        binding.categoriesList.setupRecyclerView(LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                            categoriesAdapter)
                    }

                }
                is NetworkRequest.Error->{
                    binding.homeLoading.visible(false,binding.categoriesList)
                    binding.root.showSnackBar(response.message.toString(),R.color.philippineSilver)
                }
            }
            //click Adapter
            categoriesAdapter.setOnItemClickListener {
                val videoFragment = VideoFragment()
                val bundle = Bundle()
                bundle.putInt(CATEGORY_ID, it.id.toString().toInt())
                bundle.putInt(FROM, 0)
                bundle.putInt(TO, 10)
                videoFragment.arguments = bundle
                videoFragment.show(parentFragmentManager, videoFragment.tag)
            }
        }
    }

    private fun loadSpecialFromApi(){
        viewModel.loadSpecial()
        viewModel.specialLiveData.observe(viewLifecycleOwner){response->
            when(response){
                is NetworkRequest.Loading->{

                }
                is NetworkRequest.Success->{
                    response.data.let {
                        videoAdapter.setData(it!!)
                        binding.videoRecycler.setupRecyclerView(
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL, false
                            ), videoAdapter
                        )
                    }

                }
                is NetworkRequest.Error->{}
            }
            //click
            videoAdapter.setOnItemClickListener {video->
                goToDetail(video)
            }
        }
    }

    private fun loadBestFromApi(){
        viewModel.loadBest()
        viewModel.bestLiveData.observe(viewLifecycleOwner){response->
            when(response){
                is NetworkRequest.Loading->{

                }
                is NetworkRequest.Success->{
                    response.data.let {
                        videoAdapter.setData(it!!)
                        binding.videoRecycler.setupRecyclerView(
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL, false
                            ), videoAdapter
                        )
                    }

                }
                is NetworkRequest.Error->{}
            }
            //click
            videoAdapter.setOnItemClickListener {video->
                goToDetail(video)
            }
        }
    }

    private fun loadNewFromApi(){
        viewModel.loadNew()
        viewModel.newLiveData.observe(viewLifecycleOwner){response->
            when(response){
                is NetworkRequest.Loading->{

                }
                is NetworkRequest.Success->{
                    response.data.let {
                        videoAdapter.setData(it!!)
                        binding.videoRecycler.setupRecyclerView(
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL, false
                            ), videoAdapter
                        )
                    }

                }
                is NetworkRequest.Error->{

                }
            }
            //click
            videoAdapter.setOnItemClickListener {video->
                goToDetail(video)
            }
        }
    }


    private fun goToDetail(video:ResponseVideoList.ResponseVideoListItem){
        val direction = HomeFragment2Directions.actionToDetail(video)
        findNavController().navigate(direction)
    }


    private fun showInternetStatus(hasNet:Boolean) {
        binding.apply {
            if (!hasNet) {
                disconnectedLay.visible(true, homeContentLay)
                initDisconnectInternet()
            } else {
                disconnectedLay.visible(false, homeContentLay)
            }
        }
    }


    private fun initDisconnectInternet(){
        binding.apply {
           emptyLay.txtEmptyList.text=getString(R.string.please_check_internet)
           emptyLay.txtEmptyList.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_wifi_slash,0,0)
           emptyLay.txtEmptyList.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
           emptyLay.txtEmptyList.compoundDrawables[1].setTint(ContextCompat.getColor(requireContext(),R.color.red))
        }
    }


    fun loadSpecialList() { loadSpecialFromApi()}

    fun loadBestList() { loadBestFromApi() }

    fun loadNewList() { loadNewFromApi() }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}