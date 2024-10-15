package com.sample.data.search.di

import android.content.SharedPreferences
import com.sample.data.BuildConfig
import com.sample.data.search.SearchRepositoryImpl
import com.sample.data.search.localdatasource.SearchPreferencesManager
import com.sample.data.search.remotedatasource.SearchAPIService
import com.sample.domain.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    companion object {
        @Provides
        fun provideBaseUrl() = BuildConfig.SEARCH_URL

        @Provides
        @Singleton
        fun provideAPIService(retrofit: Retrofit): SearchAPIService = retrofit.create(
            SearchAPIService::class.java
        )

        @Provides
        @Singleton
        fun provideSearchPreferencesManager(
            sharedPreferences: SharedPreferences
        ): SearchPreferencesManager = SearchPreferencesManager(sharedPreferences)
    }
}