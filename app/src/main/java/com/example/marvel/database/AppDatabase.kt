package com.example.marvel.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.marvel.database.dao.HeroesDAO
import com.example.marvel.model.Hero

@Database(
    entities = [Hero::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getHeroDao(): HeroesDAO

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "marvel_heroes_db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}