package com.example.recipes_app.di

import com.example.recipes_app.data.datastore.impl.FilterSettingsRepositoryImpl
import com.example.recipes_app.data.network.impl.RecipesDetailsRepositoryImpl
import com.example.recipes_app.data.network.impl.RecipesPagingRepositoryImpl
import com.example.recipes_app.domain.api.FilterSettingsRepository
import com.example.recipes_app.domain.api.RecipesDetailsRepository
import com.example.recipes_app.domain.api.RecipesPagingRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<RecipesDetailsRepository> {
        RecipesDetailsRepositoryImpl(get())
    }

    single<RecipesPagingRepository> {
        RecipesPagingRepositoryImpl(get())
    }

    single<FilterSettingsRepository> {
        FilterSettingsRepositoryImpl(get())
    }

}