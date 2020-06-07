package br.eti.arnaud.pokemon.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.eti.arnaud.pokemon.R
import br.eti.arnaud.pokemon.repository.entity.Pokemon
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_pokemon.view.*

class PokemonsRecyclerViewAdapter(
    var pokemons: List<Pokemon>,
    val pokemonClick: (id: Long) -> Unit
) : RecyclerView.Adapter<PokemonsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pokemon, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItemView(pokemons[position])
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.itemView).clear(holder.itemView.itemImageView)
    }

    fun update(updatedPokemons: List<Pokemon>) {
        pokemons = updatedPokemons
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItemView(item: Pokemon) {
            if (item.image.isNotEmpty()) {
                Glide.with(itemView)
                    .load(item.image)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.ic_pokeball)
                    .into(itemView.itemImageView)
            }
            itemView.titleTextView.text = item.name
            itemView.setOnClickListener {
                pokemonClick(item.id)
            }
        }
    }
}