package io.prashant.starwars.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.prashant.starwars.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


object Networking {

    private const val DEFAULT_READ_TIMEOUT = 60L
    private const val DEFAULT_WRITE_TIMEOUT = 60L
    private const val DEFAULT_CONNECTION_TIMEOUT = 20L

    fun create(
        baseUrl: String,
        cacheDir: File,
        cacheSize: Long,
    ): NetworkService {
        val gson: Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .cache(Cache(cacheDir, cacheSize))
                    .addInterceptor(HttpLoggingInterceptor()
                        .apply {
                            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            else HttpLoggingInterceptor.Level.NONE
                        })
                    .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(NetworkService::class.java)
    }

}