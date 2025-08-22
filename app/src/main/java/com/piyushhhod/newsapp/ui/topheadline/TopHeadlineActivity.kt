package com.piyushhhod.newsapp.ui.topheadline

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.piyushhhod.newsapp.NewsApplication
import com.piyushhhod.newsapp.data.model.Article
import com.piyushhhod.newsapp.databinding.ActivityTopHeadlineBinding
import com.piyushhhod.newsapp.di.component.DaggerActivityComponent
import com.piyushhhod.newsapp.di.module.ActivityModule
import com.piyushhhod.newsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {

    @Inject
    lateinit var topHeadlineViewModel : TopHeadlineViewModel

    @Inject
    lateinit var adapter : TopHeadlineAdapter


    private val tag = "TopHeadlineActivity"
    private lateinit var binding: ActivityTopHeadlineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
        setupObserver()
        Log.i(tag,"TopHeadlineActivity is started")

    }

    private fun setUI(){
        val recyclerView = binding.topHeadlinesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )

        recyclerView.adapter = adapter

    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                topHeadlineViewModel.uiState.collect{
                    when(it){
                        is UiState.Error -> {
                            binding.topHeadlinePBar.visibility = View.VISIBLE
                            Toast.makeText(this@TopHeadlineActivity , it.message , Toast.LENGTH_LONG)
                                .show()
                        }
                        UiState.Loading -> {
                            binding.topHeadlinePBar.visibility = View.VISIBLE
                            binding.topHeadlinesRecyclerView.visibility = View.GONE
                        }
                        is UiState.Success -> {
                            binding.topHeadlinePBar.visibility = View.GONE
                            renderList(it.data)
                            binding.topHeadlinesRecyclerView.visibility= View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(articleList : List<Article>){
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()

    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}
