package com.example.pokedex.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PokemonSpecies {
    @SerializedName("base_happiness")
    @Expose
    var baseHappiness: Int? = null

    @SerializedName("capture_rate")
    @Expose
    var captureRate: Int? = null

    @SerializedName("color")
    @Expose
    var color: Color? = null

    @SerializedName("egg_groups")
    @Expose
    var eggGroups: List<EggGroup>? = null

    @SerializedName("evolution_chain")
    @Expose
    var evolutionChain: EvolutionChain? = null

    @SerializedName("evolves_from_species")
    @Expose
    var evolvesFromSpecies: EvolvesFromSpecies? = null

    @SerializedName("flavor_text_entries")
    @Expose
    var flavorTextEntries: List<FlavorTextEntry>? = null

    @SerializedName("form_descriptions")
    @Expose
    var formDescriptions: List<Any>? = null

    @SerializedName("forms_switchable")
    @Expose
    var formsSwitchable: Boolean? = null

    @SerializedName("gender_rate")
    @Expose
    var genderRate: Int? = null

    @SerializedName("genera")
    @Expose
    var genera: List<Genera>? = null

    @SerializedName("generation")
    @Expose
    var generation: Generation? = null

    @SerializedName("growth_rate")
    @Expose
    var growthRate: GrowthRate? = null

    @SerializedName("habitat")
    @Expose
    var habitat: Habitat? = null

    @SerializedName("has_gender_differences")
    @Expose
    var hasGenderDifferences: Boolean? = null

    @SerializedName("hatch_counter")
    @Expose
    var hatchCounter: Int? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("is_baby")
    @Expose
    var isBaby: Boolean? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("names")
    @Expose
    var names: List<Name>? = null

    @SerializedName("order")
    @Expose
    var order: Int? = null

    @SerializedName("pal_park_encounters")
    @Expose
    var palParkEncounters: List<PalParkEncounter>? = null

    @SerializedName("pokedex_numbers")
    @Expose
    var pokedexNumbers: List<PokedexNumber>? = null

    @SerializedName("shape")
    @Expose
    var shape: Shape? = null

    @SerializedName("varieties")
    @Expose
    var varieties: List<Variety>? = null

}

internal class Area {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}

class EggGroup {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}

class EvolutionChain {
    @SerializedName("url")
    @Expose
    var url: String? = null

}

class EvolvesFromSpecies {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}

class Genera {
    @SerializedName("genus")
    @Expose
    var genus: String? = null

    @SerializedName("language")
    @Expose
    internal var language: Language_? = null

}

class Generation {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}

class GrowthRate {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}

class Habitat {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}

class Language {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}

internal class Language_ {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}

internal class Language__ {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}

class Name {
    @SerializedName("language")
    @Expose
    internal var language: Language__? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

}

class PalParkEncounter {
    @SerializedName("area")
    @Expose
    internal var area: Area? = null

    @SerializedName("base_score")
    @Expose
    var baseScore: Int? = null

    @SerializedName("rate")
    @Expose
    var rate: Int? = null

}

internal class Pokedex {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}

class PokedexNumber {
    @SerializedName("entry_number")
    @Expose
    var entryNumber: Int? = null

    @SerializedName("pokedex")
    @Expose
    internal var pokedex: Pokedex? = null

}

class Shape {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}

class Variety {
    @SerializedName("is_default")
    @Expose
    var isDefault: Boolean? = null

    @SerializedName("pokemon")
    @Expose
    var pokemon: Pokemon? = null

}

class Version {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}