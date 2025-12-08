package com.example.recipes_app.di

import com.example.recipes_app.domain.api.FilterSettingsInteractor
import com.example.recipes_app.domain.api.RecipesDetailsInteractor
import com.example.recipes_app.domain.api.RecipesPagingInteractor
import com.example.recipes_app.domain.impl.FilterSettingsInteractorImpl
import com.example.recipes_app.domain.impl.RecipesDetailsInteractorImpl
import com.example.recipes_app.domain.impl.RecipesPagingInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<RecipesDetailsInteractor> {
        RecipesDetailsInteractorImpl(get())
    }

    single<RecipesPagingInteractor> {
        RecipesPagingInteractorImpl(get())
    }

    single<FilterSettingsInteractor> {
        FilterSettingsInteractorImpl(get())
    }

}