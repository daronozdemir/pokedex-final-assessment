package com.example.pokedex.Model

import java.util.ArrayList

class PokemonResponse(count: Int, next: String?, previous: String?, results: ArrayList<Pokemon>?) {
    var count = 0
    var next: String? = null
    var previous: String? = null
    var results: ArrayList<Pokemon>? = null

    init {
        this.count = count
        this.next = next
        this.previous = previous
        this.results = results
    }
}