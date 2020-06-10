package com.example.pokedex.Model

data class PokemonDetail(val abilities: List<Ability>, val baseExperience: Int,
                         val forms: List<Species>, val gameIndices: List<List<GameIndex>>,
                         val height: Double, val heldItems: List<HeldItem>, val id: Int,
                         val isDefault: Boolean, val locationAreaEncounters: String,
                         val moves: List<Move>, val name: String, val order: Int,
                         val species: Species, val sprites: Sprites, val stats: List<Stat>,
                         val types: List<Type>, val weight: Double)

data class Ability(val ability: Species, val isHidden: Boolean, val slot: Int)

data class GameIndex(val gameIndex: Int, val version: Species)

data class HeldItem(val item: Species, val versionDetails: List<VersionDetail>)

data class VersionDetail(val rarity: Int, val version: Species)

data class Move(val moves: Species, val versionGroupDetails: List<VersionGroupDetail>)

data class VersionGroupDetail(val levelLearnedAt: Int, val moveLearnMethod: Species, val versionGroup: Species)

data class Sprites(val backDefault: String, val backFemale: String, val backShiny: String,
                   val backShinyFemale: String, val frontDefault: String, val frontFemale: String,
                   val frontShiny: String, val frontShinyFemale: String)