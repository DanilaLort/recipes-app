package com.example.recipes_app.domain.api

import com.example.recipes_app.domain.models.FilterSettings
import kotlinx.coroutines.flow.Flow

interface FilterSettingsRepository {
    val filterSettings: Flow<FilterSettings>
    suspend fun updateSearchQuery(query: String)
    suspend fun updateSort(sortBy: String)
    suspend fun clearAllFilters()
    suspend fun getCurrentSettings(): FilterSettings
}