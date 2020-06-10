package com.example.pokedex.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.DB.PokedexRepository
import com.example.pokedex.Model.Pokemon
import com.example.pokedex.Model.PokemonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val pokedexRepository = PokedexRepository()
    val pokemon = MutableLiveData<List<Pokemon>?>()
    var count = 0
    val error = MutableLiveData<String?>()

    fun getPokemons(limit: Int) {
        pokedexRepository
                .getPokemons(limit)
                ?.enqueue(object : Callback<PokemonResponse?> {
                    override fun onResponse(call: Call<PokemonResponse?>, response: Response<PokemonResponse?>) {
                        if (response.isSuccessful && response.body() != null) {
                            pokemon.value = response.body()!!.results
                            count = response.body()!!.count
                        } else {
                            error.setValue("Api Error: " + response.message())
                        }
                    }

                    override fun onFailure(call: Call<PokemonResponse?>, t: Throwable) {
                        error.value = "Api Error: " + t.message
                    }
                })
    }
}