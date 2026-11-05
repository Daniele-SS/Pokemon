package com.example.apipokemon.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.apipokemon.R


@Composable
fun TelaInicial() {
    var  textoseguro by remember()
    { mutableStateOf("") }

    // Column: Organiza os elementos verticalmente (um abaixo do outro)
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF7F7F7))
    ) {

        // =========================================================================
        // SEÇÃO 1: CABEÇALHO VERMELHO (Pokébola + Título "Pokédex")
        // =========================================================================

        // Row: Organiza os elementos horizontalmente (um ao lado do outro)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC63B38))
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagem da Pokébola
            Image(
                painter = painterResource(id = R.drawable.pokeball),
                contentDescription = "Pokébola",
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Título "Pokédex"
            Text(
                text = "Pokédex",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // =========================================================================
        // SEÇÃO 2: CAMPO DE PESQUISA ("Nome ou ID")
        // =========================================================================

        OutlinedTextField(
            value = textoseguro,
            onValueChange = { textoseguro = it },

            // Texto de dica (placeholder) exibido enquanto a caixa está vazia
            placeholder = {
                Text(
                    text = "Nome ou ID",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },

            // Ícone que fica no final da caixa de texto (lado direito)
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
            singleLine = true, // Impede que o usuário dê "Enter" e crie várias linhas
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}