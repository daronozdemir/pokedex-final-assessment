package com.example.pokedex.DB

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.pokedex.DB.MyPokemonDatabase.Companion.getDatabase
import com.example.pokedex.Model.MyPokemon
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MyPokemonRepository(context: Context?) {
    private val myPokemonDatabase: MyPokemonDatabase? = getDatabase(context!!)
    private val myPokemonDao: MyPokemonDao?
    val allPokemons: LiveData<List<MyPokemon?>?>?
    private val mExecutor: Executor = Executors.newSingleThreadExecutor()

    fun insert(pokemon: MyPokemon?) {
        mExecutor.execute { myPokemonDao!!.insert(pokemon) }
    }

    fun update(pokemon: MyPokemon?) {
        mExecutor.execute { myPokemonDao!!.update(pokemon) }
    }

    fun delete(pokemon: MyPokemon?) {
        mExecutor.execute { myPokemonDao!!.delete(pokemon) }
    }

    fun deleteAllPokemons(pokemonList: List<MyPokemon?>?) {
        mExecutor.execute { myPokemonDao!!.delete(pokemonList) }
    }

    init {
        myPokemonDao = myPokemonDatabase!!.MyPokemonDao()
        allPokemons = myPokemonDao!!.allPokemons
    }
}