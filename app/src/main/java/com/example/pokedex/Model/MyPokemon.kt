package com.example.pokedex.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "my_pokemons")
class MyPokemon(id: Int, name: String?, frontImage: String?, backImage: String?, height: Double, weight: Double, hp: Int, attack: Int, defence: Int, speed: Int, specialAttack: Int, specialDefence: Int, type: String?, type2: String?, color: String?, description: String?) : Serializable {
    @PrimaryKey
    var id = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "frontImage")
    var frontImage: String? = null

    @ColumnInfo(name = "backImage")
    var backImage: String? = null

    @ColumnInfo(name = "height")
    var height = 0.0

    @ColumnInfo(name = "weight")
    var weight = 0.0

    @ColumnInfo(name = "hp")
    var hp = 0

    @ColumnInfo(name = "attack")
    var attack = 0

    @ColumnInfo(name = "defence")
    var defence = 0

    @ColumnInfo(name = "speed")
    var speed = 0

    @ColumnInfo(name = "specialAttack")
    var specialAttack = 0

    @ColumnInfo(name = "specialDefence")
    var specialDefence = 0

    @ColumnInfo(name = "type")
    var type: String? = null

    @ColumnInfo(name = "type2")
    var type2: String? = null

    @ColumnInfo(name = "color")
    var color: String? = null

    @ColumnInfo(name = "description")
    var description: String? = null

    init {
        this.id = id
        this.name = name
        this.frontImage = frontImage
        this.backImage = backImage
        this.height = height
        this.weight = weight
        this.hp = hp
        this.attack = attack
        this.defence = defence
        this.speed = speed
        this.specialAttack = specialAttack
        this.specialDefence = specialDefence
        this.type = type
        this.type2 = type2
        this.color = color
        this.description = description
    }
}