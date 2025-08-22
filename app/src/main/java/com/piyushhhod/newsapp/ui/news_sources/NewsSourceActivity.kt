package com.piyushhhod.newsapp.ui.news_sources

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
import com.piyushhhod.newsapp.data.model.SourceX
import com.piyushhhod.newsapp.databinding.ActivityNewsSourceBinding
import com.piyushhhod.newsapp.di.component.DaggerActivityComponent
import com.piyushhhod.newsapp.di.module.ActivityModule
import com.piyushhhod.newsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSourceActivity : AppCompatActivity() {
    @Inject
    lateinit var newsSourcesViewModel: NewsSourcesViewModel

    @Inject
    lateinit var adapter : NewsSourceAdapter

//    private val tag = "NewsSourceActivity"
    private lateinit var binding: ActivityNewsSourceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = ActivityNewsSourceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
        setupObserver()

    }

    private fun setUI() {
        val recyclerView = binding.sourceRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                newsSourcesViewModel.uiState.collect{
                    when(it){
                        is UiState.Error -> {
                            binding.sourceProgessBar.visibility = View.VISIBLE
                            Toast.makeText(this@NewsSourceActivity , it.message , Toast.LENGTH_LONG)
                                .show()
                        }
                        UiState.Loading -> {
                            binding.sourceProgessBar.visibility = View.VISIBLE
                            binding.sourceRecyclerView.visibility = View.GONE
                        }
                        is UiState.Success -> {
                            binding.sourceProgessBar.visibility = View.GONE
                            renderList(it.data)
                            binding.sourceRecyclerView.visibility = View.VISIBLE
                        }
                    }

                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(sourceList : List<SourceX>){
        adapter.addData(sourceList)
        adapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build()
            .inject(this)

    }
}