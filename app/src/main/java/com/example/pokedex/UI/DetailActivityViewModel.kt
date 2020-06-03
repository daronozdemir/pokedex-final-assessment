package com.example.pokedex.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.DB.PokedexRepository
import com.example.pokedex.Model.PokemonDetail
import com.example.pokedex.Model.PokemonSpecies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val pokedexRepository = PokedexRepository()
    val pokemon = MutableLiveData<PokemonDetail?>()
    val pokemonSpecie = MutableLiveData<PokemonSpecies?>()
    val error = MutableLiveData<String?>()

    fun getPokemons(name: String?) {
        pokedexRepository
                .getPokemon(name)
                ?.enqueue(object : Callback<PokemonDetail?> {
                    override fun onResponse(call: Call<PokemonDetail?>, response: Response<PokemonDetail?>) {
                        if (response.isSuccessful && response.body() != null) {
                            pokemon.setValue(response.body())
                        } else {
                            error.setValue("Api Error: " + response.message())
                        }
                    }

                    override fun onFailure(call: Call<PokemonDetail?>, t: Throwable) {
                        error.value = "Api Error: " + t.message
                    }
                })
    }

    fun getPokemonSpecies(name: String?) {
        pokedexRepository
                .getPokemonSpecies(name)
                ?.enqueue(object : Callback<PokemonSpecies?> {
                    override fun onResponse(call: Call<PokemonSpecies?>, response: Response<PokemonSpecies?>) {
                        if (response.isSuccessful && response.body() != null) {
                            pokemonSpecie.setValue(response.body())
                        } else {
                            error.value = "Api Error: ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<PokemonSpecies?>, t: Throwable) {
                        error.value = "Api Error: ${t.message}"
                    }
                })
    }
}