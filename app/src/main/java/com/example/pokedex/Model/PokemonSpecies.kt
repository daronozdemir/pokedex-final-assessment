package com.example.pokedex.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PokemonSpecies {
    @SerializedName("color")
    @Expose
    var color: Color? = null

    @SerializedName("flavor_text_entries")
    @Expose
    var flavorTextEntries: List<FlavorTextEntry>? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}

class Version {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}