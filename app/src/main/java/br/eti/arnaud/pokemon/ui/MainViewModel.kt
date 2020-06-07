package br.eti.arnaud.pokemon.ui

import androidx.lifecycle.ViewModel
import br.eti.arnaud.pokemon.repository.Pokemons

class MainViewModel : ViewModel() {

    val pokemons = Pokemons()

    fun release() {
        pokemons.release()
    }

    fun start() {
        pokemons.start()
    }
}