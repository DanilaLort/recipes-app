package com.example.recipes_app.data.datastore.api

import com.example.recipes_app.domain.models.FilterSettings
import kotlinx.coroutines.flow.Flow

interface FilterPreferencesDataSource {
    val filterSettings: Flow<FilterSettings>
    suspend fun saveFilterSettings(settings: FilterSettings)
    suspend fun clearFilters()
}