package com.example.marvel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvel.model.Hero

@Dao
interface HeroesDAO {

    @Query("SELECT * FROM Hero")
    suspend fun get(): List<Hero>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Hero>)

    @Query("SELECT * FROM Hero WHERE name LIKE '%' || :query || '%'")
    suspend fun getFiltered(query: String): List<Hero>?

}