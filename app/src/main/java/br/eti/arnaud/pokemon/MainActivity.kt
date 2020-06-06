package br.eti.arnaud.pokemon

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import br.eti.arnaud.pokemon.ui.MainViewModel
import br.eti.arnaud.pokemon.ui.main.MainFragment

class MainActivity : SearchActivity() {

    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        vm = ViewModelProvider(this)[MainViewModel::class.java]

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        vm.pokemons.load("")
        vm.pokemons.updatePokemons()
    }

    override fun onSearchTextSubmit(query: String) {
        vm.pokemons.load(query)
    }

    override fun onSearchTextChange(query: String) {
        vm.pokemons.load(query)
    }


}