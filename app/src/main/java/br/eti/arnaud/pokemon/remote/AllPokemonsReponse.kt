package br.eti.arnaud.pokemon.remote

class AllPokemonsReponse(
    val results: List<Result>
) {

    class Result(
        val name: String,
        val url: String
    )
}