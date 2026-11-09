package com.example.apipokemon.data.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeEntry>,
    val stats: List<StatEntry>,
    val abilities: List<AbilityEntry>
)

data class TypeEntry(
    val slot: Int,
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)

data class StatEntry(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)

data class AbilityEntry(
    val ability: AbilityInfo
)

data class AbilityInfo(
    val name: String
)