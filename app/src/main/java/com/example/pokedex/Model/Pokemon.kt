package com.example.pokedex.Model

data class Pokemon(val name: String, val url: String) {
    val id: Int
        get() {
            val splitUrl = url.split("/")
            return splitUrl[splitUrl.size - 2].toInt() ?: 0
        }
}