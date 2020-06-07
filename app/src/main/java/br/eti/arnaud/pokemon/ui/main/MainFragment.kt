package br.eti.arnaud.pokemon.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import br.eti.arnaud.pokemon.R
import br.eti.arnaud.pokemon.repository.entity.Pokemon
import br.eti.arnaud.pokemon.ui.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        swipeRefreshLayout.setOnRefreshListener {
            vm.pokemons.updatePokemons()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm.pokemons.obs.pokemonsList.observe(viewLifecycleOwner, Observer {
            setupView(it)
        })
    }

    private fun setupView(it: List<Pokemon>?) {
        emptyTextView.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
        swipeRefreshLayout.isRefreshing = false
        it?.let {
            if (pokemonsRecyclerView.adapter == null) {
                setupAdapter(it)
            } else {
                updateRecyclerView(it)
            }
        }
    }

    private fun setupAdapter(pokemons: List<Pokemon>) {
        pokemonsRecyclerView.adapter = PokemonsRecyclerViewAdapter(pokemons) { clickedPokemonId ->
            Toast.makeText(
                requireActivity(),
                "Pokemon clicado: $clickedPokemonId",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun updateRecyclerView(pokemons: List<Pokemon>) {
        (pokemonsRecyclerView.adapter as PokemonsRecyclerViewAdapter).update(pokemons)
    }
}