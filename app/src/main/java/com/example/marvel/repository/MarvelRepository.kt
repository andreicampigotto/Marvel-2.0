package com.example.marvel.repository

import com.example.marvel.BuildConfig.PRIVATE_KEY
import com.example.marvel.BuildConfig.PUBLIC_KEY
import com.example.marvel.database.dao.HeroesDAO
import com.example.marvel.enums.KeysDataHeroes
import com.example.marvel.model.Hero
import com.example.marvel.model.Thumbnail
import com.example.marvel.service.MarvelAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.marvel.utils.md5
//import retrofit2.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import java.util.*

class MarvelRepository @Inject constructor(
    private val service: MarvelAPI,
    private val dao: HeroesDAO,
    private val db: FirebaseFirestore,
) {

    //salva no firebase
    suspend fun addHeroes(heroes: Hero): Boolean {
        val map = mutableMapOf<String, Any>()
        map[KeysDataHeroes.ID_HERO.key] = heroes.id
        map[KeysDataHeroes.NAME.key] = heroes.name
//        map[KeysDataHeroes.THUMBNAIL.key] = heroes.thumbnail
        heroes.description?.let { map.put(KeysDataHeroes.DESCRIPTION.key, it) }

        val taks = db.collection("HEROES").add(map)
        taks.await()
        return true
    }

    //pega do room
    private suspend fun getAllFromDatabase(): List<Hero>? {
        return dao.get()
    }

    //get do firebase
    private suspend fun getAllFromFirebase(): List<Hero>? {
        val task =
            db.collection("HEROES").get()
        val result = task.await()
        val heroesList = mutableListOf<Hero>()
        result?.forEach {
            heroesList.add(
                Hero(
                    it.data[KeysDataHeroes.ID_HERO.key] as Long,
                    it.data["key_name"] as String,
//                    it.data["key_thumbnail"] as String,
                    it.data["key_description"] as String,
                )
            )
        }
        return heroesList
    }


    //salvar no room
    private suspend fun insertIntoDatabase(listOf: List<Hero>): Boolean {
        return withContext(Dispatchers.Default){
            dao.insert(listOf)
            true
        }
    }

    private suspend fun getFilteredFromDatabase(query: String): List<Hero>? {
        return dao.getFiltered(query)
    }

    //pega resultado da api
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

            // salva local Room
            resultAPI?.let {
                insertIntoDatabase(it)
            }

            //salva no firebase
            resultAPI?.forEach {
                addHeroes(it)
            }

            return resultAPI
        }

        return getAllFromDatabase()
    }

    //logica para checkar internet e ultilizar banco local ou cloud
    suspend fun fetchHeroesDb(checkInternet: Boolean): List<Hero>? {
        if (checkInternet) {
            getAllFromDatabase()?.forEach {
                addHeroes(it)
            }
            return getAllFromFirebase()
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