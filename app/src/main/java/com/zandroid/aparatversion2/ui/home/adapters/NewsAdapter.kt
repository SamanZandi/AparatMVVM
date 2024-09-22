package com.zandroid.aparatversion2.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.bumptech.glide.Glide
import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.data.model.ResponseNews
import com.zandroid.aparatversion2.data.model.ResponseNews.ResponseNewsItem
import com.zandroid.aparatversion2.databinding.ItemNewsBinding

import javax.inject.Inject

class NewsAdapter @Inject constructor():RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    //Binding
    private lateinit var binding: ItemNewsBinding
  //  private var newsList= emptyList<ResponseNewsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder:RecyclerView.ViewHolder(binding.root){
        fun bind(item: ResponseNewsItem){
            binding.apply {
              imgNews.load(item.icon){
                    crossfade(true)
                    crossfade(500)
                  memoryCachePolicy(CachePolicy.ENABLED)
                  error(R.drawable.ic_placeholder)
                }
                txtNews.text=item.title

                //click root
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener:((ResponseNewsItem) ->Unit)?=null

    fun setOnItemClickListener(listener:(ResponseNewsItem) ->Unit){
        onItemClickListener=listener
    }


    private val differCallBack=object :DiffUtil.ItemCallback<ResponseNewsItem>(){
        override fun areItemsTheSame(oldItem: ResponseNewsItem, newItem: ResponseNewsItem): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseNewsItem, newItem: ResponseNewsItem): Boolean {
            return oldItem==newItem
        }

    }

    val differ= AsyncListDiffer(this,differCallBack)


}