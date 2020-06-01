package com.example.pokedex.DB

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokedexDatabaseApi {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    fun create(): PokedexDataBaseApiService {
        // Create an OkHttpClient to be able to make a log of the network traffic
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        // Create the Retrofit instance
        val pokeApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        // Return the Retrofit MovieDatabaseApiService
        return pokeApi.create(PokedexDataBaseApiService::class.java)
    }
}