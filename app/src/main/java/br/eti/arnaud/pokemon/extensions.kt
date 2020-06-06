package br.eti.arnaud.pokemon

import androidx.appcompat.widget.SearchView
import java.util.*

fun SearchView.setOnQueryTextListener(
    textSubmit: (text: String) -> Unit,
    textChange: (text: String) -> Unit
) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let { textSubmit(query) }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let { textChange(newText) }
            return true
        }
    })
}

fun Timer.schedule(delay: Long, task: () -> Unit) {
    schedule(object : TimerTask() {
        override fun run() {
            task()
        }
    }, delay)
}