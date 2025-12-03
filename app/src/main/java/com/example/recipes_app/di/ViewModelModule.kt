package com.example.recipes_app.di

import com.example.recipes_app.presentation.main.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainActivityViewModel()
    }
}