package com.example.recipes_app.data.datastore.impl

import com.example.recipes_app.data.datastore.api.FilterPreferencesDataSource
import com.example.recipes_app.domain.api.FilterSettingsRepository
import com.example.recipes_app.domain.models.FilterSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class FilterSettingsRepositoryImpl(
    private val dataSource: FilterPreferencesDataSource
) : FilterSettingsRepository {

    override val filterSettings: Flow<FilterSettings> = dataSource.filterSettings

    override suspend fun updateSearchQuery(query: String) {
        val current = getCurrentSettings()
        dataSource.saveFilterSettings(current.copy(searchQuery = query))
    }

    override suspend fun updateSort(sortBy: String) {
        val current = getCurrentSettings()
        dataSource.saveFilterSettings(current.copy(sortBy = sortBy))
    }

    override suspend fun updateSortType(sortType: String) {
        val current = getCurrentSettings()
        dataSource.saveFilterSettings(current.copy(selectedType = sortType))
    }

    override suspend fun updateSortDirection(sortDirection: String) {
        val current = getCurrentSettings()
        dataSource.saveFilterSettings(current.copy(sortDirection = sortDirection))
    }

    override suspend fun clearAllFilters() {
        dataSource.clearFilters()
    }

    override suspend fun getCurrentSettings(): FilterSettings {
        return filterSettings.first()
    }
}