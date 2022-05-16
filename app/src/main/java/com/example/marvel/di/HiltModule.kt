package com.example.marvel.di

import android.content.Context
import com.example.marvel.database.AppDatabase
import com.example.marvel.database.dao.HeroesDAO
import com.example.marvel.repository.MarvelRepository
import com.example.marvel.service.MarvelAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://gateway.marvel.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMarvel(retrofit: Retrofit): MarvelAPI = retrofit.create(MarvelAPI::class.java)

    @Provides
    fun provideHeroDao(@ApplicationContext context: Context): HeroesDAO {
        return AppDatabase.getDatabase(context).getHeroDao()
    }

    @Provides
    fun provideHeroRepository(service: MarvelAPI, dao: HeroesDAO): MarvelRepository =
        MarvelRepository(service, dao)
}