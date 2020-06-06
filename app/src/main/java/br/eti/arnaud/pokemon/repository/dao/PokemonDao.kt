package br.eti.arnaud.pokemon.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.eti.arnaud.pokemon.repository.entity.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM Pokemon WHERE name LIKE :name")
    fun load(name: String): List<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE id = :id")
    fun load(id: Long): List<Pokemon>

    @Insert
    fun insert(pokemons: List<Pokemon>)

}