package com.piyushhhod.newsapp.ui.countries

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.piyushhhod.newsapp.NewsApplication
import com.piyushhhod.newsapp.databinding.ActivityCountriesBinding
import com.piyushhhod.newsapp.di.component.DaggerActivityComponent
import com.piyushhhod.newsapp.di.module.ActivityModule
import com.piyushhhod.newsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountriesActivity : AppCompatActivity() {

    @Inject
    lateinit var countriesViewModel: CountriesViewModel

    @Inject
    lateinit var adapter :CountriesAdapter

//    private val tag = "CountriesActivity"
    private lateinit var binding : ActivityCountriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityCountriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
        setObserver()

    }

    private fun setUI() {
        val recyclerView = binding.countriesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                countriesViewModel.uiState.collect{
                    when(it){
                        is UiState.Error -> {
                            binding.countriesProgessBar.visibility = View.GONE
                            Toast.makeText(this@CountriesActivity,it.message , Toast.LENGTH_LONG)
                                .show()
                        }
                        UiState.Loading -> {
                            binding.countriesProgessBar.visibility = View.VISIBLE
                            binding.countriesRecyclerView.visibility = View.GONE
                        }
                        is UiState.Success -> {
                            renderList(it.data)
                            binding.countriesRecyclerView.visibility = View.VISIBLE
                            binding.countriesProgessBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(list: List<String>) {
        adapter.addData(list)
        adapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }
}