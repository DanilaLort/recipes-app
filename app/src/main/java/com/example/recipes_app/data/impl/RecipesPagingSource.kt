package com.example.recipes_app.data.impl

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.example.recipes_app.data.dto.RecipeDto
import com.example.recipes_app.data.network.RecipesApi
import com.example.recipes_app.domain.models.Recipe

class RecipesPagingSource(
    private val apiService: RecipesApi,
    private val options: Map<String, String>,
) : PagingSource<Int, Recipe>() {
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {
            val position = params.key ?: 0
            val pageSize = params.loadSize

            val response = apiService.searchRecipes(
                options,
                offset = position,
                number = pageSize
            )

            Log.d("PagingData", "response RecipesPagingSource")

            val nextKey = if (response.results.isEmpty()) {
                null
            } else {
                position + pageSize
            }

            val prevKey = if (position == 0) null else position - pageSize

            LoadResult.Page(
                data = response.results.map { it.toDomain() },
                prevKey = prevKey,
                nextKey = nextKey,
                itemsBefore = if (position == 0) 0 else LoadResult.Page.COUNT_UNDEFINED,
                itemsAfter = if (nextKey == null) 0 else LoadResult.Page.COUNT_UNDEFINED
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun RecipeDto.toDomain(): Recipe {
        return Recipe(
            this.id,
            this.title,
            this.image
        )
    }

}