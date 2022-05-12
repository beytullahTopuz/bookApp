package com.t4zb.aws.modellayer.network.cache

import android.content.Context
import com.t4zb.aws.modellayer.network.NetworkUtils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File

object CacheClient {
    fun createCachedClient(context: Context): OkHttpClient? {
        val cacheDirectory = File(context.cacheDir, "cache_file")
        val cacheSize = Cache(cacheDirectory, 20 * 1024 * 1024)

        val interceptor = Interceptor {
            val originalResponse = it.proceed(it.request())
            return@Interceptor if (NetworkUtils.isNetworkAvailable(context)) {
                val maxAge = 1 // read from 1 ms
                originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
            } else {
                val maxStale = 60 * 60 * 24 * 4
                originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-stale=$maxStale")
                    .build()
            }
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cache(cacheSize)
            .build()
    }
}