package com.example.pokedex.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.pokedex.DB.MyPokemonRepository
import com.example.pokedex.Model.MyPokemon

class MyPokemonViewModel(application: Application) : AndroidViewModel(application) {
    private val myPokemonRepository: MyPokemonRepository
    val pokemons: LiveData<List<MyPokemon?>?>?

    fun insert(pokemon: MyPokemon?) {
        myPokemonRepository.insert(pokemon)
    }

    fun update(pokemon: MyPokemon?) {
        myPokemonRepository.update(pokemon)
    }

    fun delete(pokemon: MyPokemon?) {
        myPokemonRepository.delete(pokemon)
    }

    fun deleteAllPokemons(pokemonList: List<MyPokemon?>?) {
        myPokemonRepository.deleteAllPokemons(pokemonList)
    }

    init {
        myPokemonRepository = MyPokemonRepository(application.applicationContext)
        pokemons = myPokemonRepository.allPokemons
    }
}