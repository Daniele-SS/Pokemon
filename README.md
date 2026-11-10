# Pokemon

Atividade de Programação para Dispositivos Mobile (Kotlin + Jetpack Compose).

App que consome a [PokeAPI](https://pokeapi.co/) e mostra os 151 Pokémon da primeira geração.

## Funcionalidades
- Lista com os 151 Pokémon (imagem, nome e número)
- Busca por nome ou ID
- Tela de detalhes com tipos, peso, altura, movimentos e status base
- Mensagens de carregamento, erro e "nenhum Pokémon encontrado"

## Estrutura
- `MainActivity.kt` — navegação entre as telas (`tela_inicial` → `tela_detalhes/{nome}`)
- `data/remote/` — `PokeApiService` (endpoints) e `RetrofitInstance` (cliente HTTP)
- `data/model/` — modelos da resposta da API
- `ui/theme/screens/` — `TelaInicial`, `TelaDetalhes` e `HomeViewModel` (estado da lista e da busca)

## Tecnologias
Retrofit + Gson (API), Coil (imagens), Navigation Compose, ViewModel
