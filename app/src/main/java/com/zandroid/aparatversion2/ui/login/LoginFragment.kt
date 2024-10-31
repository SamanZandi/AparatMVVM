package com.zandroid.aparatversion2.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.databinding.FragmentLoginBinding
import com.zandroid.aparatversion2.databinding.FragmentRegisterBinding
import com.zandroid.aparatversion2.utils.showSnackBar
import com.zandroid.aparatversion2.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class LoginFragment : Fragment() {

    //Binding
    private var _binding: FragmentLoginBinding?= null
    private val binding get() = _binding!!

    //Others
    private val viewModel:RegisterViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            viewModel.stateLiveData.observe(viewLifecycleOwner){
                if (it.code!!>0){
                    viewModel.saveState(it.code)
                    root.showSnackBar("welcome :)",R.color.green)
                    findNavController().popBackStack(R.id.loginFragment,true)
                    findNavController().navigate(R.id.actionToHome)
                }else{
                    root.showSnackBar("Your login is not successful",R.color.philippineSilver)
                }
            }



            //click
            btnLogin.setOnClickListener {
                //fill info
                val username=usernameEdt.text.toString()
                val password=passEdt.text.toString()
                if (username.isNotEmpty() && password.isNotEmpty()){
                    viewModel.loginUser(username,password)

                }else{
                    root.showSnackBar("Please Fill Both Fields!",R.color.red)
                }
            }

            txtRegister.setOnClickListener{
                findNavController().popBackStack(R.id.loginFragment,true)
                findNavController().navigate(R.id.registerFragment)
            }

        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}