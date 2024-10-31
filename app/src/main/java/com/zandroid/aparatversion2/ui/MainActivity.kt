package com.zandroid.aparatversion2.ui


import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.databinding.ActivityMainBinding
import com.zandroid.aparatversion2.ui.home.HomeFragment2
import com.zandroid.aparatversion2.utils.BEST
import com.zandroid.aparatversion2.utils.NEW
import com.zandroid.aparatversion2.utils.SPECIAL
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //Binding
    private var _binding:ActivityMainBinding?=null
    private val binding get() = _binding!!

    private lateinit var navHost:NavHostFragment
    private var selectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set Toolbar
        setSupportActionBar(binding.toolbar)
        navHost=supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment

        binding.bottomNav.setupWithNavController(navHost.navController)

        navHost.navController.addOnDestinationChangedListener{_,destination,_ ->
          when(destination.id){
              R.id.splashFragment ->menuVisible(false)
              R.id.detailFragment ->menuVisible(false)
              R.id.registerFragment->menuVisible(false)
              R.id.loginFragment->menuVisible(false)
              else->menuVisible(true)
          }

        }



            //Filter
            binding.toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.filter -> {
                        filter()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.exit->{
                        navHost.navController.navigate(R.id.loginFragment)
                        return@setOnMenuItemClickListener true
                    }

                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }

    }




    private fun menuVisible(isVisible:Boolean){
        binding.apply {
            if (isVisible){
                toolbar.visibility= View.VISIBLE
                bottomNav.visibility= View.VISIBLE
            }else{
                toolbar.visibility= View.GONE
               bottomNav.visibility= View.GONE
            }
        }
    }

    //show menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    //make Alert Dialog
    private fun filter() {
        val builder = AlertDialog.Builder(this)
        val videoList = arrayOf(SPECIAL, BEST, NEW)
        builder.setSingleChoiceItems(videoList, selectedItem) { dialog, item ->
            val frag=navHost.childFragmentManager.fragments.get(0) as HomeFragment2
           when (item) {
                0 -> { frag.loadSpecialList() }
                1 -> { frag.loadBestList() }
                2 -> { frag.loadNewList() }
            }

            selectedItem = item
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp() || navHost.navController.navigateUp()
    }
}