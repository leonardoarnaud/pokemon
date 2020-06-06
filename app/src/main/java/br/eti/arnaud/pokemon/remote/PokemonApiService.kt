package br.eti.arnaud.pokemon.remote

import retrofit2.http.GET

interface PokemonApiService {
    @GET("pokemon?limit=964")
    suspend fun allPokemons(): AllPokemonsReponse
}