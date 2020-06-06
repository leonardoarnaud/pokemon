package br.eti.arnaud.pokemon.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Pokemon(
    @PrimaryKey
    val id: Long,
    val name: String,
    val image: String
)