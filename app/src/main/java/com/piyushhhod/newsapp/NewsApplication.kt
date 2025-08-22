package com.piyushhhod.newsapp

import android.app.Application
import com.piyushhhod.newsapp.di.component.ApplicationComponent
import com.piyushhhod.newsapp.di.component.DaggerApplicationComponent
import com.piyushhhod.newsapp.di.module.ApplicationModule
import dagger.internal.DaggerGenerated

class NewsApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}