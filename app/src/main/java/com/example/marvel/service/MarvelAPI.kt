package com.example.marvel.service

import com.example.marvel.model.HeroData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPI {

    @GET("/v1/public/characters")
    suspend fun fetchHeroes(
        @Query("ts") ts: String,
        @Query("apikey") apikey : String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<HeroData>

    @GET("/v1/public/characters")
    suspend fun fetchHeroesByName(
        @Query("ts") ts: String,
        @Query("apikey") apikey : String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("nameStartsWith") nameStartsWith: String
    ): Response<HeroData>
}