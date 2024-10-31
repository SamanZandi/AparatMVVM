package com.zandroid.aparatversion2.ui.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.databinding.FragmentSplashBinding
import com.zandroid.aparatversion2.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class SplashFragment : Fragment() {

    //Binding
    private var _binding:FragmentSplashBinding?= null
    private val binding get() = _binding!!

    //Others
    private val viewModel:RegisterViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleScope.launch {
                imgLogo.animate().rotation(360f).setDuration(3000).start()
                delay(3000)

            }

            viewModel.getState.observe(viewLifecycleOwner){
                if (it==1 || it>0){
                    findNavController().navigate(R.id.actionToHome)
                }else{
                    findNavController().navigate(R.id.registerFragment)
                }
                Log.e( "code: ", it.toString())
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}