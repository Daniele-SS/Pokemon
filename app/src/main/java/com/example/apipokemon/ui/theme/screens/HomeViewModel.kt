package com.example.apipokemon.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apipokemon.data.model.PokemonEntry
import com.example.apipokemon.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface PokemonListUiState {
    object Loading : PokemonListUiState
    data class Success(val list: List<PokemonEntry>) : PokemonListUiState
    data class Error(val message: String) : PokemonListUiState
}

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState

    private var originalList = listOf<PokemonEntry>()

    init {
        fetchPokemonList()
    }

    fun fetchPokemonList() {
        viewModelScope.launch {
            _uiState.value = PokemonListUiState.Loading
            try {
                val response = RetrofitInstance.api.getPokemonList(limit = 151)
                originalList = response.results
                _uiState.value = PokemonListUiState.Success(originalList)
            } catch (e: Exception) {
                _uiState.value = PokemonListUiState.Error("Falha ao carregar lista de Pokémon.")
            }
        }
    }

    fun searchPokemon(query: String) {
        if (query.isBlank()) {
            _uiState.value = PokemonListUiState.Success(originalList)
        } else {
            val filtered = originalList.filter {
                it.name.contains(query.trim().lowercase(), ignoreCase = true) ||
                        it.id.toString() == query.trim()
            }
            _uiState.value = PokemonListUiState.Success(filtered)
        }
    }
}