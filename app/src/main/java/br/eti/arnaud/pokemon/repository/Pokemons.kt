package br.eti.arnaud.pokemon.repository

import android.annotation.SuppressLint
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

    val obs = PublicObservables()

    private var _currentQuery: String = ""

    private val _remotePokemons = MutableLiveData<AllPokemonsReponse>()
    private val _remotePokemonsObserver = Observer<AllPokemonsReponse> {
        it?.let {
            AsyncTask.execute {
                save(it)
                loadFromDb()
                obs.loading.postValue(false)
            }
        }
    }

    fun start() {
        _remotePokemons.observeForever(_remotePokemonsObserver)
    }

    private fun loadFromDb() {
        AsyncTask.execute {
            val id = _currentQuery.toLongOrNull()
            App.instance.db.pokemonDao().let {
                obs.pokemonsList.postValue(
                    capitalizeNames(
                        when {
                            id != null -> it.load(id)
                            _currentQuery.isEmpty() -> it.loadAll()
                            else -> it.load("%$_currentQuery%")
                        }
                    )
                )
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun capitalizeNames(pokemons: List<Pokemon>): List<Pokemon> {
        return pokemons.mapTo(arrayListOf()) {
            Pokemon(
                id = it.id,
                name = it.name.capitalize(),
                image = it.image
            )
        }
    }

    fun updatePokemons() {
        obs.loading.postValue(true)
        GlobalScope.launch {
            if (obs.pokemonsList.value == null) {
                search(_currentQuery)
            }
            _remotePokemons.postValue(App.instance.client.allPokemons())
        }
    }

    private fun save(allPokemonsResponse: AllPokemonsReponse) {
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

    fun search(query: String) {
        _currentQuery = query
        loadFromDb()
    }

    fun release() {
        _remotePokemons.removeObserver(_remotePokemonsObserver)
    }

    class PublicObservables {
        val pokemonsList = MutableLiveData<List<Pokemon>>()
        val loading = MutableLiveData<Boolean>()
    }
}