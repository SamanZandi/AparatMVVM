package com.zandroid.aparatversion2.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.data.model.ResponseCategory.ResponseCategoryItem
import com.zandroid.aparatversion2.data.model.ResponseVideoList
import com.zandroid.aparatversion2.data.model.ResponseVideoList.ResponseVideoListItem
import com.zandroid.aparatversion2.databinding.ItemCategoryBinding
import com.zandroid.aparatversion2.databinding.ItemVideoBinding
import com.zandroid.aparatversion2.databinding.ItemVideoVer2Binding
import javax.inject.Inject

class VideoAdapter @Inject constructor():RecyclerView.Adapter<VideoAdapter.ViewHolder>(){

    //Binding
    private lateinit var binding: ItemVideoVer2Binding
    private var videoList= emptyList<ResponseVideoListItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemVideoVer2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videoList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    inner class ViewHolder:RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item: ResponseVideoListItem){
            binding.apply {
               imgVideo.load(item.icon){
                    crossfade(true)
                    crossfade(500)
                }

                txtTitle.text=item.title
                txtTime.text="duration: ${item.time}"
                txtViews.text="views: ${item.view}"


                //click
                 txtDetail.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }

            }
        }
    }

    private var onItemClickListener:((ResponseVideoListItem) ->Unit)?=null

    fun setOnItemClickListener(listener:(ResponseVideoListItem) ->Unit){
        onItemClickListener=listener
    }


    fun setData(data: List<ResponseVideoListItem>){
        val catDiffUtil=CatDiffUtils(videoList,data)
        val diffUtils=DiffUtil.calculateDiff(catDiffUtil)
        //full list
        videoList=data
        //set adapter
        diffUtils.dispatchUpdatesTo(this)
    }


    class CatDiffUtils(private val oldItem:List<ResponseVideoListItem>, private val newItem:List<ResponseVideoListItem>):DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
           return oldItem[oldItemPosition]===newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition]===newItem[newItemPosition]
        }

    }
}

