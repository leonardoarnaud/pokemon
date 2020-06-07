package br.eti.arnaud.pokemon.ui

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.eti.arnaud.pokemon.nonNull
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity() {

    abstract val vm: MainViewModel

    private val loadingObserver = Observer<Boolean> {
        progressBar?.visibility = if (it) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()

        vm.pokemons.obs.loading.nonNull().observeForever(loadingObserver)
    }

    override fun onPause() {
        super.onPause()

        vm.pokemons.obs.loading.nonNull().removeObserver(loadingObserver)
    }

}