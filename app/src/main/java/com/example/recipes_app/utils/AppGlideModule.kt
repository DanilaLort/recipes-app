package com.example.recipes_app.utils

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.recipes.BuildConfig

@GlideModule
class AppGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        // Настройки по умолчанию для всех загрузок
        val requestOptions = RequestOptions()
            .format(DecodeFormat.PREFER_RGB_565) // Экономим память
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .timeout(15000) // 15 секунд таймаут
            .encodeQuality(85) // Качество JPEG 85%

        builder.setDefaultRequestOptions(requestOptions)

        // Настройка кэша в памяти (20% от доступной памяти)
        val memoryCacheSize = (Runtime.getRuntime().maxMemory() / 8).toLong() // 12.5% от max памяти
        builder.setMemoryCache(LruResourceCache(memoryCacheSize))

        // Настройка дискового кэша (250 MB)
        val diskCacheSize = 250L * 1024 * 1024 // 250 MB
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSize))

        // Логирование (только для debug сборок)
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(android.util.Log.DEBUG)
        }
    }

    override fun isManifestParsingEnabled(): Boolean = false
}