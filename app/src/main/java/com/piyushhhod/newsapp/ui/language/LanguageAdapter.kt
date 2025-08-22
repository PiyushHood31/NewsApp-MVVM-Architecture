package com.piyushhhod.newsapp.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piyushhhod.newsapp.databinding.SourceItemLayoutBinding

class LanguageAdapter(
    private val LanguageList: ArrayList<String> = ArrayList()
):RecyclerView.Adapter<LanguageAdapter.DataViewHolder>() {
    class DataViewHolder(private val binding: SourceItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(language: String){
           binding.sourceItem.text = language
       }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            SourceItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    override fun getItemCount(): Int = LanguageList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(LanguageList[position])

    fun addItem(list : List<String>){
        LanguageList.addAll(list)
    }
}