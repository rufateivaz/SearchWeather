package com.sample.data.di

import android.content.Context
import android.content.SharedPreferences
import com.sample.data.BuildConfig
import com.sample.data.SearchRepositoryImpl
import com.sample.data.localdatasource.SearchPreferencesManager
import com.sample.data.remotedatasource.SearchAPIService
import com.sample.domain.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {
    @Provides
    fun provideBaseUrl() = BuildConfig.SEARCH_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): SearchAPIService = retrofit.create(
        SearchAPIService::class.java
    )

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSearchPreferencesManager(sharedPreferences: SharedPreferences): SearchPreferencesManager {
        return SearchPreferencesManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideRepository(
        apiService: SearchAPIService,
        searchPreferencesManager: SearchPreferencesManager
    ): SearchRepository = SearchRepositoryImpl(apiService, searchPreferencesManager)
}