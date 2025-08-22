package com.piyushhhod.newsapp.ui.news_sources

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piyushhhod.newsapp.data.model.SourceX
import com.piyushhhod.newsapp.databinding.SourceItemLayoutBinding
import com.piyushhhod.newsapp.ui.topheadline.TopHeadlineActivity


class NewsSourceAdapter (
    private val sourceList : ArrayList<SourceX>
):RecyclerView.Adapter<NewsSourceAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: SourceItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(sourceX: SourceX){
            binding.sourceItemText.text = sourceX.name
            binding.sourceItem.setOnClickListener {
                Log.d("Adapter", "Clicked item is ${binding.sourceItemText.text}")
                val context = it.context
                val intent = Intent(context, TopHeadlineActivity::class.java)
                intent.putExtra("source_name", sourceX.name)
                context.startActivity(intent)
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