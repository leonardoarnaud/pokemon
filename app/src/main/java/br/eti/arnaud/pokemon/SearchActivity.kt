package br.eti.arnaud.pokemon

import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import com.bumptech.glide.Glide
import java.util.*


abstract class SearchActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private var searchTimer: Timer = Timer()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        searchView = (menu?.findItem(R.id.action_search)?.actionView as SearchView)
        searchView.apply {
            maxWidth = Integer.MAX_VALUE
            setOnQueryTextListener({
                onSearchTextSubmit(it)
            }, {
                searchTimer.cancel()
                searchTimer = Timer()
                searchTimer.schedule(SEARCH_DELAY) {
                    runOnUiThread {
                        onSearchTextChange(it)
                    }
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    var searchSuggestions: List<Pair<String, String>>? = null
        set(value) {
            val cursor = MatrixCursor(arrayOf("_id", "image", "title"))
            value?.mapIndexed { i, v ->
                cursor.newRow()
                    .add("_id", i)
                    .add("image", v.first)
                    .add("title", v.second)
            }
            searchView.suggestionsAdapter = object : CursorAdapter(this, cursor, false) {
                override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
                    return layoutInflater.inflate(R.layout.item_search_suggestion, parent, false)
                }

                override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
                    view?.let {
                        Glide.with(it)
                            .load(cursor?.getString(1))
                            .into(it.findViewById(R.id.itemImageView))
                        it.findViewById<TextView>(R.id.titleTextView).text = cursor?.getString(2)
                    }
                }
            }
            field = value
        }

    abstract fun onSearchTextSubmit(text: String)
    abstract fun onSearchTextChange(text: String)

    companion object {
        private const val SEARCH_DELAY: Long = 300
    }
}

