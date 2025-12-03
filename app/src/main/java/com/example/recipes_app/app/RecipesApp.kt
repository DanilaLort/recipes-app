package com.example.recipes_app.app

import android.app.Application
import com.example.recipes_app.di.dataModule
import com.example.recipes_app.di.interactorModule
import com.example.recipes_app.di.repositoryModule
import com.example.recipes_app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RecipesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RecipesApp)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }
}