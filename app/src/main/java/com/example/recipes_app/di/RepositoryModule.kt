package com.example.recipes_app.di

import com.example.recipes_app.data.impl.RecipesDetailsRepositoryImpl
import com.example.recipes_app.data.impl.RecipesPagingRepositoryImpl
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

}