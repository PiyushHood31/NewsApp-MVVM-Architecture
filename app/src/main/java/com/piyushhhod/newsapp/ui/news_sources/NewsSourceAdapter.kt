package com.piyushhhod.newsapp.ui.news_sources

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piyushhhod.newsapp.data.model.SourceX
import com.piyushhhod.newsapp.databinding.SourceItemLayoutBinding


class NewsSourceAdapter (
    private val sourceList : ArrayList<SourceX>
):RecyclerView.Adapter<NewsSourceAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: SourceItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(sourceX: SourceX){
            binding.sourceItem.text = sourceX.name
            itemView.setOnClickListener{
                Log.d("NewsSourceAdapter" ,"${sourceX.name} is Clicked")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
       DataViewHolder(
           SourceItemLayoutBinding.inflate(
               LayoutInflater.from(parent.context),
               parent,
               false
           )
       )


    override fun getItemCount(): Int  = sourceList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int)  =
        holder.bind(sourceList[position])

    fun addData(list : List<SourceX>){
        sourceList.addAll(list)
    }
}