package com.example.apipokemon.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.apipokemon.data.model.PokemonResponse
import com.example.apipokemon.data.remote.RetrofitInstance

@Composable
fun TelaDetalhes(
    pokemonName: String,
    onBackClick: () -> Unit
) {
    var pokemon by remember { mutableStateOf<PokemonResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(pokemonName) {
        try {
            pokemon = RetrofitInstance.api.getPokemon(pokemonName.lowercase().trim())
            isLoading = false
        } catch (e: Exception) {
            isError = true
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFFC63B38))
        }
        return
    }

    if (isError || pokemon == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Não foi possível carregar os detalhes.", color = Color.Red)
        }
        return
    }

    val p = pokemon!!
    val primaryType = p.types.firstOrNull()?.type?.name ?: "normal"
    val mainColor = getPokemonTypeColor(primaryType)
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${p.id}.png"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mainColor)
    ) {
        // CABEÇALHO DA TELA (Nome + ID + Botão de Voltar)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 16.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = p.name.replaceFirstChar { it.uppercase() },
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "#${p.id.toString().padStart(3, '0')}",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // CARD BRANCO (Conteúdo Inferior)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(top = 220.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp, start = 24.dp, end = 24.dp, bottom = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TAGS DOS TIPOS (Grass, Poison, etc)
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    p.types.forEach { typeEntry ->
                        val typeName = typeEntry.type.name
                        Surface(
                            shape = CircleShape,
                            color = getPokemonTypeColor(typeName),
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = typeName.replaceFirstChar { it.uppercase() },
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // TÍTULO ABOUT
                Text(
                    text = "About",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = mainColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                // INFORMAÇÕES DE PESO, ALTURA E MOVES (Habilidades)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${p.weight / 10.0} kg",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Weight", color = Color.Gray, fontSize = 12.sp)
                    }

                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp)
                            .background(Color(0xFFE0E0E0))
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${p.height / 10.0} m",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Height", color = Color.Gray, fontSize = 12.sp)
                    }

                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp)
                            .background(Color(0xFFE0E0E0))
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val abilitiesStr = p.abilities.take(2).joinToString("\n") {
                            it.ability.name.replaceFirstChar { char -> char.uppercase() }
                        }
                        Text(
                            text = abilitiesStr,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Moves", color = Color.Gray, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // TÍTULO BASE STATS
                Text(
                    text = "Base Stats",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = mainColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                // LISTA DE ESTATÍSTICAS (HP, ATK, DEF, SATK, SDEF, SPD)
                p.stats.forEach { stat ->
                    StatRow(
                        statName = formatStatName(stat.stat.name),
                        statValue = stat.baseStat,
                        color = mainColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // IMAGEM CENTRALIZADA SOBRE O CARD
        AsyncImage(
            model = imageUrl,
            contentDescription = p.name,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopCenter)
                .padding(top = 70.dp)
        )
    }
}

@Composable
fun StatRow(statName: String, statValue: Int, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = statName,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.width(48.dp)
        )

        Box(
            modifier = Modifier
                .height(16.dp)
                .width(1.dp)
                .background(Color(0xFFE0E0E0))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = statValue.toString().padStart(3, '0'),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            modifier = Modifier.width(36.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        LinearProgressIndicator(
            progress = { (statValue / 200f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}

fun formatStatName(name: String): String {
    return when (name.lowercase()) {
        "hp" -> "HP"
        "attack" -> "ATK"
        "defense" -> "DEF"
        "special-attack" -> "SATK"
        "special-defense" -> "SDEF"
        "speed" -> "SPD"
        else -> name.take(4).uppercase()
    }
}

fun getPokemonTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "grass" -> Color(0xFF74CB48)
        "poison" -> Color(0xFFA43E9E)
        "fire" -> Color(0xFFF57D31)
        "water" -> Color(0xFF6493EB)
        "bug" -> Color(0xFFA7B723)
        "normal" -> Color(0xFFAAA67F)
        "electric" -> Color(0xFFF9CF30)
        "ground" -> Color(0xFFDEC16B)
        "fairy" -> Color(0xFFE69EAC)
        "fighting" -> Color(0xFFC12239)
        "psychic" -> Color(0xFFFB5584)
        "rock" -> Color(0xFFB69E31)
        "ghost" -> Color(0xFF70559B)
        "ice" -> Color(0xFF9AD6DF)
        "dragon" -> Color(0xFF7037F9)
        else -> Color(0xFF68A090)
    }
}