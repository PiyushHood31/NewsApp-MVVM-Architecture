package com.piyushhhod.newsapp.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.piyushhhod.newsapp.NewsApplication
import com.piyushhhod.newsapp.data.model.Article
import com.piyushhhod.newsapp.databinding.ActivitySearchBinding
import com.piyushhhod.newsapp.di.component.DaggerActivityComponent
import com.piyushhhod.newsapp.di.module.ActivityModule
import com.piyushhhod.newsapp.ui.base.UiState
import com.piyushhhod.newsapp.ui.topheadline.TopHeadlineAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    @Inject
    lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    private val searchScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        observeSearchResults()
        setupSearchListener()

    }


    private fun setupUI() {
        val recyclerView = binding.searchRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupSearchListener() {
        binding.searchField.textChanges()
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                val queryText = query?.toString()?.trim() ?: ""

                if (queryText.isEmpty()) {
                    clearResults()
                } else {
                    viewModel.onSearchQueryChanged(queryText)
                }
            }
            .launchIn(searchScope)
    }



    private fun observeSearchResults() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            binding.searchProgressBar.visibility = View.VISIBLE
                            binding.infoTextView.visibility = View.GONE
                        }

                        is UiState.Success -> {
                            binding.searchProgressBar.visibility = View.GONE
                            if (state.data.isNotEmpty()) {
                                renderList(state.data)
                                binding.searchRecyclerView.visibility = View.VISIBLE
                                binding.infoTextView.visibility = View.GONE
                            } else {
                                binding.infoTextView.text = "The List is Empty ...!! :("
                                binding.infoTextView.visibility = View.VISIBLE
                            }
                        }

                        is UiState.Error -> {
                            binding.searchProgressBar.visibility = View.GONE
                            binding.infoTextView.text = "Some Unexcepted error occur ..!! ;#"
                            binding.infoTextView.visibility = View.VISIBLE
                            Toast.makeText(this@SearchActivity, state.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        searchScope.cancel()
    }

    @SuppressLint("NotifyDataSetChanged")
    private  fun renderList(list: List<Article>){
        adapter.addData(list)
        adapter.notifyDataSetChanged()
    }

    fun EditText.textChanges(): Flow<CharSequence?> = callbackFlow {
        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                trySend(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        addTextChangedListener(watcher)
        awaitClose { removeTextChangedListener(watcher) }
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }

    private fun clearResults() {
        adapter.clearData()
        adapter.notifyDataSetChanged()
        binding.searchRecyclerView.visibility = View.GONE
        binding.infoTextView.text = "Start typing to search..."
        binding.infoTextView.visibility = View.VISIBLE
    }
}


