package com.capstone.sampahin.data.api

import com.capstone.sampahin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    private fun createRetrofit(baseUrl: String): Retrofit {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getApiService(): ApiService {
        val retrofit = createRetrofit(BuildConfig.BASE_URL)
        return retrofit.create(ApiService::class.java)
    }

    fun getMLApiService(): MLApiService {
        val retrofit = createRetrofit(BuildConfig.ML_BASE_URL)
        return retrofit.create(MLApiService::class.java)
    }

    fun getMapApiService(): ApiService {
        val retrofit = createRetrofit(BuildConfig.MAP_BASE_URL)
        return retrofit.create(ApiService::class.java)
    }

    fun getChatApiService(): ChatApiService {
        val retrofit = createRetrofit(BuildConfig.CHAT_BASE_URL)
        return retrofit.create(ChatApiService::class.java)
    }
}

