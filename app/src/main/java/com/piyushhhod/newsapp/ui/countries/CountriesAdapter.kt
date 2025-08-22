package com.piyushhhod.newsapp.ui.countries


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piyushhhod.newsapp.databinding.SourceItemLayoutBinding

class CountriesAdapter(
    private val countriesList: ArrayList<String> = ArrayList()
) : RecyclerView.Adapter<CountriesAdapter.DataViewHolder>() {
    class DataViewHolder(private val binding: SourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: String) {
            binding.sourceItem.text = country
            itemView.setOnClickListener {
                Log.d("CountriesAdapter", "Clicked on $it")
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