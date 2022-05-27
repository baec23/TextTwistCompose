package com.baec.texttwistcompose.di

import com.baec.texttwistcompose.data.remote.WordApi
import com.baec.texttwistcompose.repository.WordRepositoryImpl
import com.baec.texttwistcompose.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWordRepository(
        api: WordApi
    ) = WordRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideWordApi(): WordApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WordApi::class.java)
    }
}