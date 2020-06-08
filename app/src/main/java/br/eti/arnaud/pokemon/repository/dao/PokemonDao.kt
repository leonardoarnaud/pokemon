package br.eti.arnaud.pokemon.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.eti.arnaud.pokemon.repository.entity.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM Pokemon WHERE name LIKE :name ORDER BY name ASC")
    fun load(name: String): List<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE id = :id ORDER BY name ASC")
    fun load(id: Long): List<Pokemon>

    @Query("SELECT * FROM Pokemon ORDER BY name ASC")
    fun loadAll(): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemons: List<Pokemon>)

}