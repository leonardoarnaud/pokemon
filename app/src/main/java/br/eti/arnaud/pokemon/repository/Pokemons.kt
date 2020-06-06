package br.eti.arnaud.pokemon.repository

import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import br.eti.arnaud.pokemon.App
import br.eti.arnaud.pokemon.BuildConfig
import br.eti.arnaud.pokemon.remote.AllPokemonsReponse
import br.eti.arnaud.pokemon.repository.entity.Pokemon
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Pokemons {

    val obs = Observables()

    private var currentQuery: String = ""

    private val _remotePokemons = MutableLiveData<AllPokemonsReponse>()
    private val remoteObserver = Observer<AllPokemonsReponse> {
        it?.let {
            AsyncTask.execute {
                savePokemons(it)
                load(currentQuery)
            }
        }
    }

    init {
        _remotePokemons.observeForever(remoteObserver)
    }

    fun load(query: String) {
        currentQuery = query
        AsyncTask.execute {
            val id = query.toLongOrNull()
            App.instance.db.pokemonDao().let {
                obs.pokemonList.postValue(
                    if (id != null) it.load(id)
                    else it.load("'%$query%'")
                )
            }
        }
    }

    fun updatePokemons() {
        GlobalScope.launch {
            _remotePokemons.postValue(App.instance.client.allPokemons())
        }
    }

    private fun savePokemons(allPokemonsResponse: AllPokemonsReponse) {
        App.instance.db.pokemonDao().insert(
            allPokemonsResponse.results.mapTo(arrayListOf(), { result ->
                (allPokemonsResponse.results.indexOf(result) + 1).toLong().let { id ->
                    Pokemon(
                        id = id,
                        name = result.name,
                        image = String.format(BuildConfig.DEFAULT_PHOTO_URL, id)
                    )
                }
            })
        )
    }

    fun clear() {
        _remotePokemons.removeObserver(remoteObserver)
    }

    class Observables {
        val pokemonList = MutableLiveData<List<Pokemon>>()
    }
}