package com.example.pokedex.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokedex.Model.MyPokemon

@Database(entities = [MyPokemon::class], version = 1, exportSchema = false)
abstract class MyPokemonDatabase : RoomDatabase() {
    abstract fun MyPokemonDao(): MyPokemonDao?

    companion object {
        private const val NAME_DATABASE = "my_pokemon_database"

        @Volatile
        private var INSTANCE: MyPokemonDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): MyPokemonDatabase? {
            if (INSTANCE == null) {
                synchronized(MyPokemonDatabase::class.java) {
                    if (INSTANCE == null) {
                        // Create database here
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                MyPokemonDatabase::class.java, NAME_DATABASE)
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}