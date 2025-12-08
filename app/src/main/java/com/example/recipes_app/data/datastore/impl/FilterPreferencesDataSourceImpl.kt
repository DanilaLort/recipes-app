package com.example.recipes_app.data.datastore.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.recipes_app.data.datastore.api.FilterPreferencesDataSource
import com.example.recipes_app.domain.models.FilterSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class FilterPreferencesDataSourceImpl(
    val dataStore: DataStore<Preferences>
) : FilterPreferencesDataSource {

    private object Keys {
        val SEARCH_QUERY = stringPreferencesKey("search_query")
        val SELECTED_TYPE = stringPreferencesKey("selected_type")
        val SORT_BY = stringPreferencesKey("sort_by")
        val SORT_DIRECTION = stringPreferencesKey("sort_direction")
    }

    override val filterSettings: Flow<FilterSettings> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            FilterSettings(
                searchQuery = preferences[Keys.SEARCH_QUERY] ?: "",
                selectedType = preferences[Keys.SELECTED_TYPE],
                sortBy = preferences[Keys.SORT_BY] ?: "popularity",
                sortDirection = preferences[Keys.SORT_DIRECTION] ?: "desc"
            )
        }

    override suspend fun saveFilterSettings(settings: FilterSettings) {
        dataStore.edit { preferences ->
            preferences[Keys.SEARCH_QUERY] = settings.searchQuery
            settings.selectedType?.let { preferences[Keys.SELECTED_TYPE] = it }
            preferences[Keys.SORT_BY] = settings.sortBy
        }
    }

    override suspend fun clearFilters() {
        dataStore.edit { it.clear() }
    }
}