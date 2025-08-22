package com.piyushhhod.newsapp.di.component

import android.content.Context
import com.piyushhhod.newsapp.NewsApplication
import com.piyushhhod.newsapp.data.api.NetworkService
import com.piyushhhod.newsapp.data.repository.TopHeadlineRepository
import com.piyushhhod.newsapp.di.ApplicationContext
import com.piyushhhod.newsapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getContext() : Context
    fun getNetworkService() :  NetworkService
    fun getTopHeadlineRepository() : TopHeadlineRepository
}