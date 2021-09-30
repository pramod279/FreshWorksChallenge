package com.freshworks.challenge.di

import android.app.Application
import com.freshworks.challenge.data.db.AppDatabase
import com.freshworks.challenge.model.dao.FavouritesDao
import com.freshworks.challenge.repository.GiphyApiService
import com.freshworks.challenge.repository.RemoteInjector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: Pramod Selvaraj
 * @Date: 26.09.2021
 *
 * Freshworks Challenge App Modules
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun getRetroServiceInstance(): GiphyApiService {
        return RemoteInjector.injectGiphyApiService()
    }

    @Provides
    @Singleton
    fun getAppDatabase(context: Application): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun getAppDao(appDatabase: AppDatabase): FavouritesDao {
        return appDatabase.getFavouritesDao()
    }
}