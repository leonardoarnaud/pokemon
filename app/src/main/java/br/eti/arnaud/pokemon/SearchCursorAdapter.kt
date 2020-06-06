package br.eti.arnaud.pokemon

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter

class SearchCursorAdapter(
    private val layoutInflater: LayoutInflater,
    context: Context?,
    c: Cursor?,
    autoRequery: Boolean
) :
    CursorAdapter(context, c, autoRequery) {

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return layoutInflater.inflate(R.layout.item_pokemon, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        TODO("Not yet implemented")
    }
}