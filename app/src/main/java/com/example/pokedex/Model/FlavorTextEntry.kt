package com.example.pokedex.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FlavorTextEntry {
    @SerializedName("flavor_text")
    @Expose
    var flavorText: String? = null

    @SerializedName("version")
    @Expose
    var version: Version? = null

}