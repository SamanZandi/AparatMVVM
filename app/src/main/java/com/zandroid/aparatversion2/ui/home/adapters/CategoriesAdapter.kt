package com.zandroid.aparatversion2.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zandroid.aparatversion2.R
import com.zandroid.aparatversion2.data.model.ResponseCategory
import com.zandroid.aparatversion2.data.model.ResponseCategory.ResponseCategoryItem
import com.zandroid.aparatversion2.databinding.ItemCategoryBinding
import javax.inject.Inject

class CategoriesAdapter @Inject constructor():RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){

    //Binding
    private lateinit var binding: ItemCategoryBinding
    private var categoriesList= emptyList<ResponseCategoryItem>()
    private var selectedItem=-1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoriesList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    inner class ViewHolder:RecyclerView.ViewHolder(binding.root){
        fun bind(item: ResponseCategoryItem){
            binding.apply {
                catImg.load(item.icon){
                    crossfade(true)
                    crossfade(500)
                }
                catTitle.text=item.title


                //click root
                root.setOnClickListener {
                    selectedItem=adapterPosition
                   notifyDataSetChanged()
                    onItemClickListener?.let {
                        it(item)
                    }
                }
                //change color
                if (selectedItem==adapterPosition){
                    root.setBackgroundResource(R.drawable.bg_rounded_selected)
                }else{
                    root.setBackgroundResource(R.drawable.bg_rounded_white)
                }

            }
        }
    }

    private var onItemClickListener:((ResponseCategoryItem) ->Unit)?=null

    fun setOnItemClickListener(listener:(ResponseCategoryItem) ->Unit){
        onItemClickListener=listener
    }


    fun setData(data: List<ResponseCategoryItem>){
        val catDiffUtil=CatDiffUtils(categoriesList,data)
        val diffUtils=DiffUtil.calculateDiff(catDiffUtil)
        //full list
        categoriesList=data
        //set adapter
        diffUtils.dispatchUpdatesTo(this)
    }


    class CatDiffUtils(private val oldItem:List<ResponseCategoryItem>, private val newItem:List<ResponseCategoryItem>):DiffUtil.Callback(){
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

