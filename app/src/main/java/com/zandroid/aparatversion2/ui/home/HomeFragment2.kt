package com.zandroid.aparatversion2.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.muddassir.connection_checker.ConnectionState
import com.muddassir.connection_checker.checkConnection
import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.databinding.FragmentHome2Binding
import com.zandroid.aparatversion2.ui.home.adapters.CategoriesAdapter
import com.zandroid.aparatversion2.ui.home.adapters.NewsAdapter
import com.zandroid.aparatversion2.ui.home.adapters.VideoAdapter
import com.zandroid.aparatversion2.utils.CATEGORY_ID
import com.zandroid.aparatversion2.utils.FROM
import com.zandroid.aparatversion2.utils.TO
import com.zandroid.aparatversion2.utils.isNetworkAvailable
import com.zandroid.aparatversion2.utils.network.CheckConnection
import com.zandroid.aparatversion2.utils.setupRecyclerView
import com.zandroid.aparatversion2.utils.visible
import com.zandroid.aparatversion2.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject


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



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHome2Binding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    homeLoading.visible(true, homeContentLay)
                } else {
                    homeLoading.visible(false, homeContentLay)
                }
            }
            //News
            viewModel.loadNews()
            viewModel.newsListLiveData.observe(viewLifecycleOwner) { news ->
                newsAdapter.differ.submitList(news)
                newsList.setupRecyclerView(LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false), newsAdapter)
                pagerSnapHelper.attachToRecyclerView(newsList)
                indicator.attachToRecyclerView(newsList, pagerSnapHelper)
            }
            //Categories
            viewModel.loadCategories()
            viewModel.categoriesLiveData.observe(viewLifecycleOwner) { category ->
                categoriesAdapter.setData(category)
                categoriesList.setupRecyclerView(LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false), categoriesAdapter
                )

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

            //Special
            loadSpecialList()
            viewModel.specialLiveData.observe(viewLifecycleOwner) {
                videoAdapter.setData(it)
                videoRecycler.setupRecyclerView(
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL, false
                    ), videoAdapter
                )
                //click
                videoAdapter.setOnItemClickListener { video ->
                    val direction = HomeFragment2Directions.actionToDetail(video)
                    findNavController().navigate(direction)
                }
            }

            //Best
            viewModel.bestLiveData.observe(viewLifecycleOwner) {
                videoAdapter.setData(it)
                videoRecycler.setupRecyclerView(
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL, false
                    ), videoAdapter
                )
                //click
                videoAdapter.setOnItemClickListener { video ->
                    val direction = HomeFragment2Directions.actionToDetail(video)
                    findNavController().navigate(direction)
                }
            }

            //New
            viewModel.newLiveData.observe(viewLifecycleOwner) {
                videoAdapter.setData(it)
                videoRecycler.setupRecyclerView(
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL, false
                    ), videoAdapter
                )
                //click
                videoAdapter.setOnItemClickListener { video ->
                    val direction = HomeFragment2Directions.actionToDetail(video)
                    findNavController().navigate(direction)
                }
            }



           viewModel.hasNet.observe(viewLifecycleOwner) {
                if (it) {
                    showInternetStatus(true)
                } else {
                    showInternetStatus(false)
                }
                Log.e("hasNet ", it.toString())

            }
        }

        //checkInternet
       checkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                showInternetStatus(true)
            } else {
                showInternetStatus(false)
            }
            Log.e("InternetStatus ", isConnected.toString())
        }
 /*       checkConnection(this){
            when(it){
                ConnectionState.CONNECTED->{
                    showInternetStatus(true)

                }
                ConnectionState.DISCONNECTED->{
                    showInternetStatus(false)

                     }
                else->{showInternetStatus(false)}
            }
        }*/

    }


    private fun showInternetStatus(hasNet: Boolean) {
        binding.apply {
            if (!hasNet) {
                emptyList.visible(true, homeContentLay)
                emptyLay.emptyImg.setImageResource(R.drawable.disconnect_internet)
                emptyLay.emptyImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
                emptyLay.emptyTxt.setText(getString(R.string.please_check_internet))
                emptyLay.emptyTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                category.visibility=View.GONE
                videoListTxt.visibility=View.GONE

            } else {
                category.visibility=View.VISIBLE
                videoListTxt.visibility=View.VISIBLE
                emptyList.visible(false, homeContentLay)
            }
        }
    }


    fun loadSpecialList() { viewModel.loadSpecial() }

    fun loadBestList() { viewModel.loadBest() }

    fun loadNewList() { viewModel.loadNew() }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}