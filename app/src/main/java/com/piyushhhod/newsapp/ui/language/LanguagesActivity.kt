package com.piyushhhod.newsapp.ui.language

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
import com.piyushhhod.newsapp.databinding.ActivityLanguagesBinding
import com.piyushhhod.newsapp.di.component.DaggerActivityComponent
import com.piyushhhod.newsapp.di.module.ActivityModule
import com.piyushhhod.newsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguagesActivity : AppCompatActivity() {
    @Inject
    lateinit var languageViewModel: LanguageViewModel

    @Inject
    lateinit var adapter: LanguageAdapter

    private lateinit var binding: ActivityLanguagesBinding

    //    private  val TAG ="LanguageActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityLanguagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUi()
        setupObserver()


    }

    private fun setUi() {
        val recyclerView = binding.languagesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                languageViewModel.uiState.collect {
                    when (it) {
                        is UiState.Error -> {
                            binding.languagesProgessBar.visibility = View.GONE
                            binding.languagesRecyclerView.visibility = View.GONE
                            Toast.makeText(this@LanguagesActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }

                        UiState.Loading -> {
                            binding.languagesRecyclerView.visibility = View.GONE
                            binding.languagesProgessBar.visibility = View.VISIBLE
                        }

                        is UiState.Success -> {
                            renderList(it.data)
                            binding.languagesRecyclerView.visibility = View.VISIBLE
                            binding.languagesProgessBar.visibility = View.GONE

                        }
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(list: List<String>) {
        adapter.addItem(list)
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