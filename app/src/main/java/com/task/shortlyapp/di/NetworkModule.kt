package com.task.shortlyapp.di

import com.task.shortlyapp.utils.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * Created by Muhammad Maqsood on 09/07/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun okHttpClient(): OkHttpClient {
        return RetrofitClient.getHttpClient()
    }

    @Provides
    fun converterFactory(): Converter.Factory = RetrofitClient.getGsonConverter()

    @Provides
    fun retrofit(httpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return RetrofitClient.getRetrofit(httpClient, converterFactory)
    }
}
