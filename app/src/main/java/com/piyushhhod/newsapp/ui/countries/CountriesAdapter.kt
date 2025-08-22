package com.piyushhhod.newsapp.ui.countries


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piyushhhod.newsapp.databinding.SourceItemLayoutBinding
import com.piyushhhod.newsapp.ui.topheadline.TopHeadlineActivity
import com.piyushhhod.newsapp.utils.AppConstant.countryCodeToNameMap

class CountriesAdapter(
    private val countriesList: ArrayList<String> = ArrayList()
) : RecyclerView.Adapter<CountriesAdapter.DataViewHolder>() {
    class DataViewHolder(private val binding: SourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: String) {
            binding.sourceItemText.text = country
            val countryNameToCodeMap = countryCodeToNameMap.entries.associate { (code, name) -> name to code }

            binding.sourceItem.setOnClickListener {
                val countryName = binding.sourceItemText.text.toString()
                val countryCode = countryNameToCodeMap[countryName]

                if (countryCode != null) {
                    val context = it.context
                    val intent = Intent(context, TopHeadlineActivity::class.java)
                    intent.putExtra("country_name", countryCode)
                    context.startActivity(intent)
                } else {
                    Log.e("Adapter", "Country code not found for: $countryName")
                }
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder  =
        DataViewHolder(
            SourceItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int  = countriesList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(countriesList[position])

    fun addData(list : List<String>){
        countriesList.addAll(list)
    }


}