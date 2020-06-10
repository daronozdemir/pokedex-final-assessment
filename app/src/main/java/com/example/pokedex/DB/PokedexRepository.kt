package com.example.pokedex.DB

import com.example.pokedex.Model.PokemonDetail
import com.example.pokedex.Model.PokemonResponse
import com.example.pokedex.Model.PokemonSpecies
import retrofit2.Call


class PokedexRepository {
    private val OFFSET = 0
    private val pokedexDataBaseApiService = PokedexDatabaseApi.create()
    fun getPokemons(limit: Int): Call<PokemonResponse?>? {
        return pokedexDataBaseApiService.getPokemons(limit, OFFSET)
    }

    fun getPokemon(name: String?): Call<PokemonDetail?>? {
        return pokedexDataBaseApiService.getPokemon(name)
    }

    fun getPokemonSpecies(name: String?): Call<PokemonSpecies?>? {
        return pokedexDataBaseApiService.getPokemonSpecies(name)
    }
}