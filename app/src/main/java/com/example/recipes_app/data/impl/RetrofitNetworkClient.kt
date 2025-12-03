package com.example.recipes_app.data.impl

import com.example.recipes_app.data.dto.RecipeDetailsRequest
import com.example.recipes_app.data.dto.Response
import com.example.recipes_app.data.network.NetworkClient
import com.example.recipes_app.data.network.RecipesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RetrofitNetworkClient(
    val recipesApi: RecipesApi
) : NetworkClient {

    companion object {
        private const val RETROFIT_LOG = "RETROFIT_LOG"
        private const val GATEWAY_TIMEOUT = 504
        private const val SUCCESSFUL_REQUEST = 200
        private const val BAD_REQUEST = 400
        private const val CONNECT_ERR = 300
    }

    override suspend fun doRequest(dto: Any): Response {
        return withContext(Dispatchers.IO) {
            try {
                if (dto is RecipeDetailsRequest) {

                    val details = recipesApi.getRecipeInformation(dto.id)
                    details.apply { resultCode = SUCCESSFUL_REQUEST }

                } else {

                    Response().apply { resultCode = BAD_REQUEST }

                }

            } catch (e: HttpException) {

                if (e.code() == GATEWAY_TIMEOUT) {

                    Response().apply {
                        resultCode = CONNECT_ERR
                    }

                } else {

                    Response().apply {
                        resultCode = BAD_REQUEST
                    }

                }
            }
        }
    }

}