package com.piyushhhod.newsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.piyushhhod.newsapp.databinding.ActivityMainBinding
import com.piyushhhod.newsapp.ui.news_sources.NewsSourceActivity
import com.piyushhhod.newsapp.ui.topheadline.TopHeadlineActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()

    }

    private fun setupUI() {
        binding.topHeadlines.setOnClickListener {
            Log.i(tag , "TopHeadlineActivity is getting started")
            val intent = Intent(this@MainActivity , TopHeadlineActivity::class.java)
            startActivity(intent)
        }

        binding.newsSources.setOnClickListener {
            Log.d(tag , "NewsSources Activity is getting started")
            val intent = Intent(this@MainActivity , NewsSourceActivity::class.java)
            startActivity(intent)
        }
    }
}