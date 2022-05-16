package com.example.marvel.repository

import com.example.marvel.BuildConfig.PRIVATE_KEY
import com.example.marvel.BuildConfig.PUBLIC_KEY
import com.example.marvel.database.dao.HeroesDAO
import com.example.marvel.model.Hero
import com.example.marvel.service.MarvelAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.marvel.utils.md5
import retrofit2.Response

class MarvelRepository @Inject constructor(
    private val service: MarvelAPI,
    private val dao: HeroesDAO
) {

    private suspend fun getAllFromDatabase(): List<Hero>? {
        return dao.get()
    }

    private suspend fun insertIntoDatabase(listOf: List<Hero>): Boolean {
        return withContext(Dispatchers.Default){
            dao.insert(listOf)
            true
        }
    }

    private suspend fun getFilteredFromDatabase(query: String): List<Hero>? {
        return dao.getFiltered(query)
    }

    suspend fun fetchHeroes(offset: Int, checkInternet: Boolean): List<Hero>? {
        val ts = (System.currentTimeMillis() / 1000).toString()
        if (checkInternet) {
            val resultAPI = withContext(Dispatchers.Default) {
                val response = service.fetchHeroes(
                    ts = ts,
                    apikey = PUBLIC_KEY,
                    hash = md5(
                        "$ts$PRIVATE_KEY$PUBLIC_KEY"
                    ),
                    offset = offset,
                    limit = 20,
                )
                val processResponse = processData(response)
                processResponse?.data?.results
            }
            resultAPI?.let {
                insertIntoDatabase(it)
            }
            return resultAPI
        }
        return getAllFromDatabase()
    }

    private fun <T> processData(response: Response<T>): T? {
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun fetchHeroesByName(offset: Int, name: String, checkInternet: Boolean): List<Hero>? {
        val ts = (System.currentTimeMillis() / 1000).toString()
        if (checkInternet) {
            return withContext(Dispatchers.Default) {
                val response = service.fetchHeroesByName(
                    ts = ts,
                    apikey = PUBLIC_KEY,
                    hash = md5(
                        "$ts$PRIVATE_KEY$PUBLIC_KEY"
                    ),
                    offset = offset,
                    nameStartsWith = name
                )
                val processResponse = processData(response)
                processResponse?.data?.results
            }
        }
        return getFilteredFromDatabase(query = name)
    }

}