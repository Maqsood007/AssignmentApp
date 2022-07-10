package com.task.shortlyapp.di

import android.content.Context
import com.task.shortlyapp.repository.locale.ShortlyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = ShortlyDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideMediaDao(database: ShortlyDatabase) = database.shortlyLinkDao()
}
