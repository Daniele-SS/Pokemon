package com.example.apipokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apipokemon.ui.theme.APIPokemonTheme
import com.example.apipokemon.ui.theme.Screens.TelaDetalhes
import com.example.apipokemon.ui.theme.Screens.TelaInicial

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APIPokemonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "tela_inicial",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // 1. Rota da Tela Inicial
                        composable(route = "tela_inicial") {
                            TelaInicial(
                                onPokemonClick = { pokemonName ->
                                    navController.navigate("tela_detalhes/$pokemonName")
                                }
                            )
                        }

                        // 2. Rota da Tela de Detalhes
                        composable(
                            route = "tela_detalhes/{pokemonName}",
                            arguments = listOf(
                                navArgument("pokemonName") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
                            TelaDetalhes(
                                pokemonName = pokemonName,
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}