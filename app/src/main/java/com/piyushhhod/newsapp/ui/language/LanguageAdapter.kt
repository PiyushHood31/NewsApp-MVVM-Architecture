package com.piyushhhod.newsapp.ui.language

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piyushhhod.newsapp.databinding.SourceItemLayoutBinding
import com.piyushhhod.newsapp.ui.topheadline.TopHeadlineActivity
import com.piyushhhod.newsapp.utils.AppConstant.languageCodeToNameMap

class LanguageAdapter(
    private val LanguageList: ArrayList<String> = ArrayList()
):RecyclerView.Adapter<LanguageAdapter.DataViewHolder>() {
    class DataViewHolder(private val binding: SourceItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(language: String){
           binding.sourceItemText.text = language
           val languageNameToCodeMap = languageCodeToNameMap.entries.associate { (code, name) -> name to code }

           binding.sourceItem.setOnClickListener {
               val languageName = binding.sourceItemText.text.toString()
               val languageCode = languageNameToCodeMap[languageName]

               if (languageCode != null) {
                   val context = it.context
                   val intent = Intent(context, TopHeadlineActivity::class.java)
                   intent.putExtra("language_name", languageCode)
                   context.startActivity(intent)
               } else {
                   Log.e("Adapter", "Language code not found for: $languageName")
               }
           }

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