package com.task.shortlyapp.di

import com.task.shortlyapp.repository.server.ShortlyAppAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    internal fun shortlyAppAPI(retrofit: Retrofit): ShortlyAppAPI = retrofit.create(
        ShortlyAppAPI::class.java
    )
}
