package com.piyushhhod.newsapp.di.component

import com.piyushhhod.newsapp.di.ActivityScope
import com.piyushhhod.newsapp.di.module.ActivityModule
import com.piyushhhod.newsapp.ui.countries.CountriesActivity
import com.piyushhhod.newsapp.ui.language.LanguagesActivity
import com.piyushhhod.newsapp.ui.news_sources.NewsSourceActivity
import com.piyushhhod.newsapp.ui.search.SearchActivity
import com.piyushhhod.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class] , modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)
    fun inject(activity: NewsSourceActivity)
    fun inject(activity: CountriesActivity)
    fun inject(activity: LanguagesActivity)
    fun inject(activity : SearchActivity)
}