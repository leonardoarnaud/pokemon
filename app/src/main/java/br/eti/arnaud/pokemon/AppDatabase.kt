package br.eti.arnaud.pokemon

import androidx.room.Database
import androidx.room.RoomDatabase
import br.eti.arnaud.pokemon.repository.dao.PokemonDao
import br.eti.arnaud.pokemon.repository.entity.Pokemon

@Database(
    entities = [
        Pokemon::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}