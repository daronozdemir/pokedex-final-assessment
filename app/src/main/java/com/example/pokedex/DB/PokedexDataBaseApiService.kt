package com.example.pokedex.DB

import com.example.pokedex.Model.PokemonDetail
import com.example.pokedex.Model.PokemonResponse
import com.example.pokedex.Model.PokemonSpecies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexDataBaseApiService {
    @GET("pokemon")
    fun getPokemons(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<PokemonResponse?>?

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String?): Call<PokemonDetail?>?

    @GET("pokemon-species/{name}")
    fun getPokemonSpecies(@Path("name") name: String?): Call<PokemonSpecies?>?
}