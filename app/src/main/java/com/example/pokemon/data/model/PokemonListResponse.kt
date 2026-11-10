package com.example.pokemon.data.model

data class PokemonListResponse(
    val results: List<PokemonEntry>
)

data class PokemonEntry(
    val name: String,
    val url: String
) {
    // Extrai o ID do Pokémon a partir da URL fornecida pela PokéAPI (ex: ".../pokemon/25/")
    val id: Int
        get() {
            return url.trimEnd('/').split("/").lastOrNull()?.toIntOrNull() ?: 1
        }

    // Imagem oficial em alta qualidade
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}