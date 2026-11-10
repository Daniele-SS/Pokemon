package com.example.pokemon.ui.theme.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pokemon.R
import com.example.pokemon.data.model.PokemonEntry
import com.example.pokemon.ui.screens.HomeViewModel
import com.example.pokemon.ui.screens.PokemonListUiState

@Composable
fun TelaInicial(
    viewModel: HomeViewModel = viewModel(),
    onPokemonClick: (String) -> Unit = {}
) {
    var textoseguro by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        // 1. Cabeçalho Pokédex
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC63B38))
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.pokeball),
                contentDescription = "Pokébola",
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Pokédex",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // 2. Campo de Busca
        OutlinedTextField(
            value = textoseguro,
            onValueChange = { novoTexto ->
                textoseguro = novoTexto
                viewModel.searchPokemon(novoTexto)
            },
            placeholder = { Text(text = "Nome ou ID", color = Color.Gray, fontSize = 14.sp) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Pesquisar",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            },
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE0E0E0),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )

        // 3. Grid de Pokémon (3 colunas)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is PokemonListUiState.Loading -> {
                    CircularProgressIndicator(color = Color(0xFFC63B38))
                }
                is PokemonListUiState.Error -> {
                    Text(text = state.message, color = Color.Red, fontSize = 16.sp)
                }
                is PokemonListUiState.Success -> {
                    if (state.list.isEmpty()) {
                        Text(text = "Nenhum Pokémon encontrado.", color = Color.Gray)
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            contentPadding = PaddingValues(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.list) { pokemon ->
                                PokemonCardItem(
                                    pokemon = pokemon,
                                    onClick = { onPokemonClick(pokemon.name) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonCardItem(
    pokemon: PokemonEntry,
    onClick: () -> Unit
) {
    // Escolhe a cor temática de acordo com o ID para simular o visual do design
    val cardColor = remember(pokemon.id) {
        when (pokemon.id % 6) {
            1 -> Color(0xFF74CB48) // Verde (ex: Bulbasaur)
            2 -> Color(0xFFF57D31) // Laranja (ex: Charmander)
            3 -> Color(0xFF6493EB) // Azul (ex: Squirtle)
            4 -> Color(0xFFA8B820) // Verde Claro (ex: Butterfree)
            5 -> Color(0xFFF9CF30) // Amarelo (ex: Pikachu)
            else -> Color(0xFF70559B) // Roxo (ex: Gastly)
        }
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Número formatado (#001, #025, etc)
            Text(
                text = "#${pokemon.id.toString().padStart(3, '0')}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = cardColor,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 6.dp, end = 8.dp)
            )

            // Imagem do Pokémon via Coil
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Faixa de rodapé colorida com o nome
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor)
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}