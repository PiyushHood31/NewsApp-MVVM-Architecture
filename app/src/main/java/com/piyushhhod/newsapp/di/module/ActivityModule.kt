package com.piyushhhod.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.piyushhhod.newsapp.data.repository.CountriesRepository
import com.piyushhhod.newsapp.data.repository.SourcesRepository
import com.piyushhhod.newsapp.data.repository.TopHeadlineRepository
import com.piyushhhod.newsapp.di.ActivityContext
import com.piyushhhod.newsapp.di.ActivityScope
import com.piyushhhod.newsapp.ui.base.ViewModelProviderFactory
import com.piyushhhod.newsapp.ui.countries.CountriesAdapter
import com.piyushhhod.newsapp.ui.countries.CountriesViewModel
import com.piyushhhod.newsapp.ui.news_sources.NewsSourceAdapter
import com.piyushhhod.newsapp.ui.news_sources.NewsSourcesViewModel
import com.piyushhhod.newsapp.ui.topheadline.TopHeadlineAdapter
import com.piyushhhod.newsapp.ui.topheadline.TopHeadlineViewModel
import dagger.Module
import dagger.Provides
import org.intellij.lang.annotations.PrintFormat

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
    fun provideNewsSourcesViewModel(newsSourcesRepository: SourcesRepository) : NewsSourcesViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(NewsSourcesViewModel::class){
                NewsSourcesViewModel(newsSourcesRepository)
            })[NewsSourcesViewModel::class.java]
    }

    @Provides
    fun provideCountriesViewModel(countriesRepository: CountriesRepository) : CountriesViewModel{
        return ViewModelProvider(activity,
            ViewModelProviderFactory(CountriesViewModel::class){
                CountriesViewModel(countriesRepository)
            })[CountriesViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun provideNewsSourceAdapter() = NewsSourceAdapter(ArrayList())

    @Provides
    fun provideCountriesAdapter() = CountriesAdapter(ArrayList())
}