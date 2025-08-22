package com.piyushhhod.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.piyushhhod.newsapp.data.repository.TopHeadlineRepository
import com.piyushhhod.newsapp.di.ActivityContext
import com.piyushhhod.newsapp.di.ActivityScope
import com.piyushhhod.newsapp.ui.base.ViewModelProviderFactory
import com.piyushhhod.newsapp.ui.topheadline.TopHeadlineAdapter
import com.piyushhhod.newsapp.ui.topheadline.TopHeadlineViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule (private val activity: AppCompatActivity){

    @ActivityContext
    @Provides
    fun provideContext() : Context{
        return  activity
    }

    @Provides
    fun provideTopHeadlineViewModel(topHeadlineRepository: TopHeadlineRepository) : TopHeadlineViewModel{
        return  ViewModelProvider(activity,
            ViewModelProviderFactory(TopHeadlineViewModel::class){
                TopHeadlineViewModel(topHeadlineRepository)
            })[TopHeadlineViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())
}