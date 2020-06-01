package com.example.pokedex.DB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pokedex.Model.MyPokemon

@Dao
interface MyPokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: MyPokemon?)

    @Delete
    fun delete(pokemon: MyPokemon?)

    @Delete
    fun delete(pokemonList: List<MyPokemon?>?)

    @Update
    fun update(pokemon: MyPokemon?)

    //List<Game> getAllGames();
    @get:Query("SELECT * FROM my_pokemons")
    val allPokemons: LiveData<List<MyPokemon?>?>?
}