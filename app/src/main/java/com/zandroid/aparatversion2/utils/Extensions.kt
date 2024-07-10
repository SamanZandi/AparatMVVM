package com.zandroid.aparatversion2.utils

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.muddassir.connection_checker.ConnectionState
import com.muddassir.connection_checker.checkConnection

fun View.visible(isShowLoading:Boolean, content: View){
    if (isShowLoading){
        this.visibility= View.VISIBLE
        content.visibility= View.GONE
    }else{
        this.visibility= View.GONE
        content.visibility= View.VISIBLE
    }
}

fun RecyclerView.setupRecyclerView(layoutManager: RecyclerView.LayoutManager, adapter: RecyclerView.Adapter<*>){
    this.layoutManager=layoutManager
    this.adapter=adapter
    this.setHasFixedSize(true)
    this.isNestedScrollingEnabled=false

}


//SnackBar
fun View.showSnackBar(message:String,color:Int){
    val snackbar= this.let { Snackbar.make(it,message,Snackbar.LENGTH_SHORT) }
    snackbar.setBackgroundTint(color)
    snackbar.show()
}




