package com.example.recipes_app.di

import com.example.recipes_app.data.impl.RetrofitNetworkClient
import com.example.recipes_app.data.network.NetworkClient
import com.example.recipes_app.data.network.RecipesApi
import com.example.recipes_app.utils.isConnected
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    single {
        Gson()
    }

    single<RecipesApi> {
        val cacheSize = 10 * 1024 * 1024L // 10 MB
        val cache = Cache(androidContext().cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                val request = chain.request()
                val url = request.url.toString()

                // Определяем стратегию кэширования по типу данных
                val cacheControl = when {
                    // Для поиска рецептов - кэшируем на 6 часов
                    url.contains("/complexSearch") -> CacheControl.Builder()
                        .maxAge(6 * 60 * 60, TimeUnit.SECONDS)
                        .build()

                    // Для деталей рецепта - кэшируем на 24 часа
                    url.contains("/information") -> CacheControl.Builder()
                        .maxAge(24 * 60 * 60, TimeUnit.SECONDS)
                        .build()

                    // Для случайных рецептов - кэшируем на 1 час
                    url.contains("/random") -> CacheControl.Builder()
                        .maxAge(60 * 60, TimeUnit.SECONDS)
                        .build()

                    // По умолчанию - 12 часов
                    else -> CacheControl.Builder()
                        .maxAge(12 * 60 * 60, TimeUnit.SECONDS)
                        .build()
                }

                // Оффлайн режим
                if (!isConnected(androidContext())) {
                    val offlineRequest = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()

                    return@addInterceptor try {
                        chain.proceed(offlineRequest)
                    } catch (e: Exception) {
                        // Возвращаем кастомный ответ с информацией об ошибке
                        Response.Builder()
                            .request(request)
                            .protocol(Protocol.HTTP_1_1)
                            .code(599) // Custom code for cache miss
                            .message("No cached data available")
                            .build()
                    }
                }

                // Онлайн режим
                val onlineRequest = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()

                chain.proceed(onlineRequest)
            }
            .addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                val request = chain.request()
                val url = request.url.toString()

                // Устанавливаем кэширование в зависимости от типа запроса
                val maxAge = when {
                    url.contains("/complexSearch") -> 6 * 60 * 60
                    url.contains("/information") -> 24 * 60 * 60
                    url.contains("/random") -> 60 * 60
                    else -> 12 * 60 * 60
                }

                response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
            }
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipesApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

}